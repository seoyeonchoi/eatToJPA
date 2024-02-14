
// 게시글에 대한 신청자 목록을 가져오는 함수
function fetchMembers(boardId) {
    return fetch(`/api/all-attention/${boardId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .catch(error => {
            console.error('There has been a problem with your fetch operation:', error);
        });
}

// 신청자 목록을 동적으로 생성하는 함수
function populateMembers(boardId) {
    console.log('populateMembers 실행')
    const dropdownMenu = document.querySelector('.dropdown-menu');
    dropdownMenu.innerHTML = ''; // 신청자 목록 초기화

    fetchMembers(boardId).then(members => {
        console.log('members: ', members);
        members.forEach(member => {
            const li = document.createElement('li');
            const a = document.createElement('a');
            a.textContent = member;
            a.classList.add('dropdown-item');
            li.appendChild(a);
            dropdownMenu.appendChild(li);
            console.log(dropdownMenu);
        });
    });
}

$(document).ready(function() {
    // 각 행을 클릭할 때 모달에 해당 게시물의 세부 정보를 표시
    $(document).on('click', 'tr', function() {
        let boardId = $(this).data('id'); // 각 행의 게시물 ID 가져오기
        let userId = document.getElementById('userId').getAttribute('data-userId');
        console.log(boardId);

        $.ajax({
            type: 'GET',
            url: '/api/board/' + boardId, // 서버에서 게시물 세부 정보 가져오는 엔드포인트
            success: function(data) {
                // 서버로부터 성공적으로 데이터를 가져왔을 때 모달 내부 업데이트
                $('#boardDetailModalTitle').text(data.title);
                var statusText = (data.completed == 0) ? '모집중' : '마감';
                var modalStatus = $('#boardDetailModalStatus');
                if (statusText === "모집중") {
                    modalStatus.css('background', '#55DDA9');
                    modalStatus.css('color', '#000000');
                } else if (statusText === "마감") {
                    modalStatus.css('background', '#868296');
                    modalStatus.css('color', '#ffffff');
                }
                $('#boardDetailModalStatus').text(statusText);
                $('#boardDetailModalMintoMax').text('최소인원 ~ 최대인원: ' + data.minNum + ' ~ ' + data.maxNum);
                $('#boardDetailModalCurrent').text('현재 신청 인원: ' + data.currentMember);
                $('#boardDetailModalWriter').text('작성자: ' + data.writer);
                $('#boardDetailModalMeetDate').text('식사일자: ' + data.meetDate);
                $('#boardDetailModalContent').text(data.content);
                $('#boardDetailModalResName').text(data.restaurantName);
                $('#boardDetailModalMeetName').text(data.meetName);
                $('#boardDetailModalReskey').text(data.restaurantKey);
                $('#boardDetailModalMeetkey').text(data.meetKey);
                $('#ModalBoardId').text(data.id);

                console.log('userId: ', userId);
                if (data.memberId === userId) {
                    // 유저의 memberId와 view가 가진 userId가 같은 경우
                    $('#modalBtn1').text('수정하기').show();
                    $('#modalBtn2').text('삭제하기').show();
                    $('#modalBtn3').text('마감하기');
                } else {
                    if (data.completed === 1) {
                        $('#modalBtn1').hide();
                        $('#modalBtn2').hide();
                        $('#modalBtn3').hide();
                    } else {
                        // 다른 경우
                        $('#modalBtn1').hide();
                        $('#modalBtn2').hide();
                        $('#modalBtn3').text('참석하기');
                    }
                }

                populateMembers(boardId);

            },
            error: function(xhr, status, error) {
                // 오류 처리
                console.error(xhr.responseText);
            }
        });

        $.ajax({
            type: 'GET',
            url: '/api/attention/' + boardId, // 서버에서 게시물 세부 정보 가져오는 엔드포인트
            success: function(data) {
                // 서버로부터 성공적으로 데이터를 가져왔을 때 모달 내부 업데이트
                if (data === true) {
                    // 유저가 참석 등록한 경우
                    $('#modalBtn1').hide();
                    $('#modalBtn2').hide();
                    $('#modalBtn3').text('참석취소');
                }

            },
            error: function(xhr, status, error) {
                // 오류 처리
                console.error(xhr.responseText);
            }
        });
    });
});

// 모달 속 버튼 클릭 이벤트 - 수정
document.getElementById('modalBtn1').addEventListener('click', event => {
    // meetDate 값을 이용하여 Date 객체 초기화
    console.log('버튼 눌렀다');
    let togo = document.getElementById('ModalBoardId').innerHTML;
    let btnContent = document.getElementById('modalBtn1').innerHTML;
    if (btnContent === '수정하기'){
        location.replace('/edit-board/' + togo);
    }
});

// 모달 속 버튼 클릭 이벤트 - 삭제
document.getElementById('modalBtn2').addEventListener('click', event => {
    // meetDate 값을 이용하여 Date 객체 초기화
    console.log('삭제버튼 눌렀다');
    let togo = document.getElementById('ModalBoardId').innerHTML;
    let btnContent = document.getElementById('modalBtn2').innerHTML;
    if (btnContent === '삭제하기') {
        if (confirm('정말 삭제하시겠습니까?')){
            fetch(`/api/boards/${togo}`, {
                    method: 'DELETE'
                }
            ).then(
                response => {
                    if (!response.ok) {
                        throw new Error("삭제 실패했습니다.");
                    }
                    return response.text();
                }).then(msg => {
                alert("게시글을 삭제했습니다.");
                location.replace(`/main`);
            });
        }
    }
});

// 모달 속 버튼 클릭 이벤트 - 마감
document.getElementById('modalBtn3').addEventListener('click', event => {
    // meetDate 값을 이용하여 Date 객체 초기화
    console.log('삭제버튼 눌렀다');
    let togo = document.getElementById('ModalBoardId').innerHTML;
    let btnContent = document.getElementById('modalBtn3').innerHTML;
    if (btnContent === '마감하기') {
        if (confirm('현재 인원으로 모임을 마감하시겠습니까?')){
            fetch(`/api/close/${togo}`, {
                    method: 'GET'
                }
            ).then(
                response => {
                    if (!response.ok) {
                        throw new Error("마감 실패했습니다.");
                    }
                    return response.text();
                }).then(msg => {
                alert("모집을 마감했습니다.\n즐거운 식사되세요!");
                location.replace(`/main`);
            });
        }
    }
});

// 모달 속 버튼 클릭 이벤트 - 참석
document.getElementById('modalBtn3').addEventListener('click', event => {
    // meetDate 값을 이용하여 Date 객체 초기화
    console.log('버튼 눌렀다');
    let boardId = document.getElementById('ModalBoardId').innerHTML;
    let btnContent = document.getElementById('modalBtn3').innerHTML;
    if (btnContent === '참석하기') {
        if (confirm('식사에 참석하시겠습니다?')){
            fetch(`/api/reservation/${boardId}`, {
                    method: 'GET'
                }
            ).then(
                response => {
                    if (!response.ok) {
                        alert("참석 등록에 실패했습니다.");
                        throw new Error("참석 등록에 실패했습니다.");
                    }
                    return response.text();
                }).then(msg => {
                alert("참석 등록에 성공했습니다.\n즐거운 식사되세요!");
                location.replace(`/main`);
            });
        }
    }
});

// 모달 속 버튼 클릭 이벤트 - 참석 취소
document.getElementById('modalBtn3').addEventListener('click', event => {
    // meetDate 값을 이용하여 Date 객체 초기화
    console.log('버튼 눌렀다');
    let boardId = document.getElementById('ModalBoardId').innerHTML;
    let btnContent = document.getElementById('modalBtn3').innerHTML;
    if (btnContent === '참석취소') {
        if (confirm('참석을 취소하시겠습니까?')){
            fetch(`/api/attention/${boardId}`, {
                    method: 'DELETE'
                }
            ).then(
                response => {
                    if (!response.ok) {
                        alert("취소에 실패했습니다.");
                        throw new Error("취소에 실패했습니다.");
                    }
                    return response.text();
                }).then(msg => {
                alert("취소했습니다.");
                location.replace(`/main`);
            });
        }
    }
});




