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
        eventClick: function(info) {
            alert('Event: ' + info.event.title);
        },
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
                        var row = '<tr>' +
                            '<th scope="row">' + board.title + '</th>' +
                            '<th scope="row">' + (board.completed == 0 ? '모집중' : '마감') + '</th>' +
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

document.addEventListener('DOMContentLoaded', function() {
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function(tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    var myModal = document.getElementById('myModal')
    var myInput = document.getElementById('myInput')
    
    myModal.addEventListener('shown.bs.modal', function () {
      myInput.focus()
    })  
});

$(document).ready(function() {
    // 각 행을 클릭할 때 모달에 해당 게시물의 세부 정보를 표시
    $('#boardsByMeetDate tr').click(function() {
        var boardId = $(this).data('id'); // 각 행의 게시물 ID 가져오기
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
                $('#boardDetailModalContent').text(data.content);
                $('#boardDetailModalResName').text(data.restaurantName);
                $('#boardDetailModalMeetName').text(data.meetName);
                $('#boardDetailModalReskey').text(data.restaurantKey);
                $('#boardDetailModalMeetkey').text(data.meetKey);

            },
            error: function(xhr, status, error) {
                // 오류 처리
                console.error(xhr.responseText);
            }
        });
    });
});

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
