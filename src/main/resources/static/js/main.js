// document.addEventListener('DOMContentLoaded', function() {
//     var calendarEl = document.getElementById('calendar');
//     var calendar = new FullCalendar.Calendar(calendarEl, {
//       initialView: 'dayGridMonth'
//     });
//     calendar.render();
//   });

var date = new Date();

document.addEventListener('DOMContentLoaded', async function() {
    let response = await fetch('/api/boards-CalendarForm');
    let boardsforCalendar = await response.json();
    console.log("boardsforCalendar:", boardsforCalendar);
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        initialDate: meetDate,
        events: boardsforCalendar,
        selectable: true,
        dayMaxEventRows: true,
        dateClick: function(info) {
            date = new Date(info.dateStr); // info.dateStr을 JavaScript Date 객체로 변환
            var month = date.getMonth() + 1; // 월 (0부터 시작하므로 1을 더해줌)
            var day = date.getDate(); // 일
            var year = date.getFullYear();

            // HTML 업데이트
            var formattedDate = month + '월 ' + day + '일의 메뉴';
            $('#boardTop .h2').text(formattedDate);

            $.ajax({
                url: '/api/boards/' + info.dateStr,
                type: 'get',
                dataType: 'json',
                success: function(data) {
                    var tbody = $('#boardsByMeetDate');
                    tbody.empty();
                    $.each(data, function(index, board) {
                        var row = '<tr data-id="' + board.id + '" data-bs-toggle="modal" data-bs-target="#boardModal">' +
                            '<th scope="row">' + board.title + '</th>' +
                            '<th scope="row">' + (board.completed === 0 ? '모집중' : '마감') + '</th>' +
                            '</tr>';
                        tbody.append(row);
                    });
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log('AJAX call failed.');
                    console.log('Status: ' + textStatus);
                    console.log('Error: ' + errorThrown);
                }
            });
        }
    });
    calendar.render();
});

function getReplyCount(boardId) {
    return fetch(`/api/reply/count/${boardId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text(); // 응답 본문을 텍스트로 읽음
        })
        .then(bodyText => {
            $('#boardDetailModalCommentAmount').text('댓글 ' + bodyText + '개');
        })
        .catch(error => {
            console.error('There has been a problem with your fetch operation:', error);
        });
}

function getReply(boardId) {
    return fetch(`/api/reply/${boardId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        }).then(replyList => {
            console.log('replies: ', replyList);
            const tableBody = document.getElementById('replyTable');
            while (tableBody.firstChild) {
                tableBody.removeChild(tableBody.firstChild);
            }

            // replyList의 각 reply 객체에 대해 반복
            replyList.forEach(reply => {
                // 새로운 행(tr)을 생성
                const row = document.createElement('tr');

                // 대댓글인 경우 들여쓰기 및 답글 표시 추가
                if (reply.id !== reply.parentId) {
                    const cell = document.createElement('td');
                    cell.colSpan = 2;
                    cell.style.paddingLeft = '20px';
                    cell.textContent = `ㄴ ${reply.memberName}: ${reply.reply}`;
                    row.appendChild(cell);
                } else {
                    // 첫 번째 셀(td)에 작성자 이름 추가
                    const writerCell = document.createElement('td');
                    writerCell.textContent = reply.memberName;
                    row.appendChild(writerCell);

                    // 두 번째 셀(td)에 댓글 내용 추가
                    const commentCell = document.createElement('td');
                    commentCell.textContent = reply.reply;
                    row.appendChild(commentCell);
                }

                // 생성한 행을 테이블에 추가
                tableBody.appendChild(row);
        });
        })
        .catch(error => {
            console.error('There has been a problem with your fetch operation:', error);
        });
}

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

// $(document).ready(function() {
//     // 각 행을 클릭할 때 모달에 해당 게시물의 세부 정보를 표시
//     $(document).on('click', '#boardsByMeetDate tr', function() {
//         let boardId = $(this).data('id'); // 각 행의 게시물 ID 가져오기
//         let userId = document.getElementById('userId').getAttribute('data-userId');
//
//         console.log(boardId);
//         $.ajax({
//             type: 'GET',
//             url: '/api/board/' + boardId, // 서버에서 게시물 세부 정보 가져오는 엔드포인트
//             success: function(data) {
//                 // 서버로부터 성공적으로 데이터를 가져왔을 때 모달 내부 업데이트
//                 $('#boardDetailModalTitle').text(data.title);
//                 var statusText = (data.completed == 0) ? '모집중' : '마감';
//                 var modalStatus = $('#boardDetailModalStatus');
//                 if (statusText === "모집중") {
//                     modalStatus.css('background', '#55DDA9');
//                     modalStatus.css('color', '#000000');
//                 } else if (statusText === "마감") {
//                     modalStatus.css('background', '#868296');
//                     modalStatus.css('color', '#ffffff');
//                 }
//                 $('#boardDetailModalStatus').text(statusText);
//                 $('#boardDetailModalMintoMax').text('최소인원 ~ 최대인원: ' + data.minNum + ' ~ ' + data.maxNum);
//                 $('#boardDetailModalCurrent').text('현재 신청 인원: ' + data.currentMember);
//                 $('#boardDetailModalWriter').text('작성자: ' + data.writer);
//                 $('#boardDetailModalMeetDate').text('식사일자: ' + data.meetDate);
//                 $('#boardDetailModalContent').text(data.content);
//                 $('#boardDetailModalResName').text(data.restaurantName);
//                 $('#boardDetailModalMeetName').text(data.meetName);
//                 $('#boardDetailModalReskey').text(data.restaurantKey);
//                 $('#boardDetailModalMeetkey').text(data.meetKey);
//                 $('#ModalBoardId').text(data.id);
//
//                 console.log('userId: ', userId);
//                 if (data.memberId === userId) {
//                     // 유저의 memberId와 view가 가진 userId가 같은 경우
//                     $('#modalBtn1').text('수정하기').show();
//                     $('#modalBtn2').text('삭제하기').show();
//                     $('#modalBtn3').text('마감하기');
//                 } else {
//                     if (data.completed === 1) {
//                         $('#modalBtn1').hide();
//                         $('#modalBtn2').hide();
//                         $('#modalBtn3').hide();
//                     } else {
//                         // 다른 경우
//                         $('#modalBtn1').hide();
//                         $('#modalBtn2').hide();
//                         $('#modalBtn3').text('참석하기');
//                     }
//                 }
//
//                 populateMembers(boardId);
//                 getReplyCount(boardId).then(r => console.log(r))
//                 getReply(boardId).then(r => console.log(r));
//
//
//             },
//             error: function(xhr, status, error) {
//                 // 오류 처리
//                 console.error(xhr.responseText);
//             }
//         });
//
//         $.ajax({
//             type: 'GET',
//             url: '/api/attention/' + boardId, // 서버에서 게시물 세부 정보 가져오는 엔드포인트
//             success: function(data) {
//                 console.log('참석취소 불러오는 함수', data)
//                 // 서버로부터 성공적으로 데이터를 가져왔을 때 모달 내부 업데이트
//                 if (data === true) {
//                     // 유저가 참석 등록한 경우
//                     $('#modalBtn1').hide();
//                     $('#modalBtn2').hide();
//                     $('#modalBtn3').text('참석취소');
//                 }
//
//             },
//             error: function(xhr, status, error) {
//                 // 오류 처리
//                 console.error(xhr.responseText);
//             }
//         });
//
//     });
// });

$(document).ready(function() {
    // 각 행을 클릭할 때 모달에 해당 게시물의 세부 정보를 표시
    $(document).on('click', '#boardsByMeetDate tr', function() {
        let boardId = $(this).data('id'); // 각 행의 게시물 ID 가져오기
        let userId = document.getElementById('userId').getAttribute('data-userId');

        console.log(boardId);

        // AJAX 요청 통합
        $.when(
            $.ajax({
                type: 'GET',
                url: '/api/board/' + boardId, // 서버에서 게시물 세부 정보 가져오는 엔드포인트
                success: function(data) {
                    // 성공적으로 데이터를 가져왔을 때 모달 내부 업데이트
                    updateModalWithData(data, userId, boardId);
                },
                error: function(xhr, status, error) {
                    // 오류 처리
                    console.error(xhr.responseText);
                }
            }),
            $.ajax({
                type: 'GET',
                url: '/api/attention/' + boardId, // 서버에서 게시물 세부 정보 가져오는 엔드포인트
                success: function(data) {
                    console.log('참석취소 불러오는 함수', data)
                    // 성공적으로 데이터를 가져왔을 때 모달 내부 업데이트
                    if (data == true) {
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
            })
        ).then(function(response1, response2) {
            // 두 개의 AJAX 요청이 모두 완료된 후에 실행되는 코드
            console.log(response1, response2);
            // 여기에 추가적인 로직을 넣을 수 있습니다.
            if (response2[0] === true) {
                // 유저가 참석 등록한 경우
                $('#modalBtn1').hide();
                $('#modalBtn2').hide();
                $('#modalBtn3').text('참석취소');
            }

        });
    });
});

// 모달 업데이트 함수
function updateModalWithData(data, userId, boardId) {
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
    getReplyCount(boardId).then(r => console.log(r))
    getReply(boardId).then(r => console.log(r));
}


// 클릭 이벤트 핸들러
document.getElementById('new-board-form').addEventListener('click', event => {
    // meetDate 값을 이용하여 Date 객체 초기화
    if (date == undefined) {
        date = new Date(meetDate);
    }
    var month = date.getMonth() + 1; // 월 (0부터 시작하므로 1을 더해줌)
    var day = date.getDate(); // 일

    // meetDate를 형식에 맞게 조합
    var meetDate = date.getFullYear() + '-' + (month < 10 ? '0' : '') + month + '-' + (day < 10 ? '0' : '') + day;

    // 해당 날짜에 유저가 이미 쓴 글이 있는지 확인
    fetch ( `/api/existsByEmailAndMeetDate/${meetDate}`, {
        method: 'GET'
        }).then(response => {
        if (!response.ok) {
            throw new Error("이미 작성한 글이 있습니다.\n같은 날에 하나의 메뉴만 등록 가능합니다!");
        }
        return response.text();
    }).then(message => {
        location.replace('/new-board/' + meetDate);
    }).catch(error => {
        alert(error.message);
    })
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
        if (confirm('식사에 참석하시겠습니까?')){
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



document.getElementById('replySubmit').addEventListener('click', event => {
    // meetDate 값을 이용하여 Date 객체 초기화
    console.log('버튼 눌렀다');
    let boardId = document.getElementById('ModalBoardId').innerHTML;
    let replyContent = document.getElementById('replyContent').value;

    fetch(`/api/reply/${boardId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            reply: replyContent
        })
    })
        .then(response => {
            if (!response.ok) {
                alert("취소에 실패했습니다.");
                throw new Error("취소에 실패했습니다.");
            } else {
                alert("댓글이 등록되었습니다.")
            }
        })
        .then(() => getReplyCount(boardId))
        .then(() => getReply(boardId))
        .then(data => console.log(data))
        .catch(error => console.error('There was a problem with the fetch operation:', error));


});
