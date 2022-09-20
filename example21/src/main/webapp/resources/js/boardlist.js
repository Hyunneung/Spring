// 선언적 함수
//1. setPaging - 페이지네이션
function setPaging(href, digit) {
	active = "";
	gray = "";
	if(href == "") { // href가 빈문자열인 경우
		if(isNaN(digit)) { // digit이 숫자가 아닌 경우 - 즉, 이전 또는 다음
			gray = " gray"; // 태그의 클래스가 될 것이므로 앞에 띄어쓰기
		} else {
			active = " active"; // 태그의 클래스가 될 것이므로 앞에 띄어쓰기
		}
	} // if-else end
	var output = "<li class='page-item" + active + "'>";
	var anchor = "<a class='page-link" + gray + "'" + href + ">" + digit + "</a></li>";
	output += anchor;		
	return output;
} // function setPaging() end

// 2. go
function go(page) {
	var limit = $("#viewcount").val(); // 줄보기 값
	var sdata = "limit=" + limit + "&state=ajax" + "&page=" + page; // ajax로 넘겨줄 데이터
	ajax(sdata);
} // function go() end

// 3. ajax
function ajax(sdata) {
	console.log(sdata); // 데이터 잘 넘어가는지 콘솔로 보기
	
	// 헤더에서 가져온 값
	var header = $("meta[name='_csrf_header']").attr("content");
	var token = $("meta[name='_csrf']").attr("content");
	$.ajax({
		type : "post",
		data : sdata,
		url : "list_ajax",
		dataType : "json",
		cache : false,
		beforeSend : function(xhr) {
			// 403 Access deny 오류 처리(Spring Security CSRF)
			// 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
			xhr.setRequestHeader(header, token);
		},
		success : function(data){
			//$("#viewcount").val(data.limit); // 줄보기 값이 넘겼던 값으로 바뀜 Q. 꼭해야하나..?
			//$("table").find("font").text("글 개수 " + data.listcount) // Q. 꼭해야하나..?
			
			// 한페이지에 줄보기 갯수만큼만 게시글 출력
			if(data.listcount > 0) {
				var num = data.listcount - (data.page - 1) * data.limit;
				console.log(num);
				
				// tbody 새로 만들기
				$("tbody").remove(); // 기존 tbody 제거
				var output = "<tbody>";
				$(data.boardlist).each(function(index, item){ // item은 하나의 게시글
					// 글의 레벨만큼 제목 앞에 공백 처리, 화살표 이미지 삽입
					blank_count = item.board_RE_LEV * 2 + 1; 
					blank = "&nbsp;"; // 공백
					for(var i = 0; i < blank_count; i++) {
						blank += "&nbsp;&nbsp;";
					}
					img = "";
					if(item.board_RE_LEV > 0) { // 원문이 아닌 경우 제목 앞에 화살표 이미지
						img = "<image src='image/line.gif'>";
					}
					
					var subject = item.board_SUBJECT.replace(/</g,'&lt;'); // (/</g, '&lt;') 의미: 모든 < 를 &lt;로 바꾸겠다! cf) g: 모든
					console.log("제목: " + subject);
					if(subject.length >= 20) { // 글 제목이 20자보다 길 경우 자르기
						subject = subject.substr(0,20) + "..."; // 0부터 20개 부분 문자열 추출
						console.log("자른 후 제목: " + subject);
					}
					
					output += '<tr><td>' + (num--) + '</td>'
						    + "<td><div>" + blank + img 
						    + "		<a href='detail?num='" + item.board_NUM + ">"
						    + 			subject + "</a>"
						    + "</div></td>";
					output += "<td><div>" + item.board_NAME + "</div></td>";  
					output += "<td><div>" + item.board_DATE + "</div></td>";  
					output += "<td><div>" + item.board_READCOUNT + "</div></td></tr>";  
				}) // each end
				output += "</tbody>";
				
				$("table").append(output); // table 완성
				
				// 페이지네이션
				$(".pagination").empty(); // 기존 페이지네이션 제거
				// 1. 이전페이지 새로 만들기
				output = "";
				digit = '이전&nbsp;';
				href = "";
				if(data.page > 1) {
					href = 'href=javascript:go(' + (data.page - 1) + ')'; // 이전페이지에도 줄보기 페이지에서 선택한 글 갯수만큼 나온다
				}
				output += setPaging(href, digit);
				
				// 2. 페이지 번호
				for(var i = data.startpage; i <= data.endpage; i++) {
					digit = i;
					href = "";
					if(i != data.page) { // 현재페이지 제외한 페이지 누르면 해당 페이지로 이동 (그 페이지도 줄보기 선택한 글 갯수만큼 나옴)
						href = 'href=javascript:go(' + i + ')';
					}
					console.log("페이지:" + digit);
					output += setPaging(href, digit);
				} // for end
				
				// 3. 다음페이지 새로 만들기
				digit = '&nbsp;다음&nbsp;';
				href = "";
				if(data.page < data.maxpage) {
					href = 'href=javascript:go(' + (data.page + 1) + ')'; // 다음페이지에도 줄보기 페이지에서 선택한 글 갯수만큼 나온다
				}
				output += setPaging(href, digit);
				
				$('.pagination').append(output);
			} // if(페이지 있을 때) end
		}, // success end
		error : function() {
			console.log('에러')
		}
	}) // ajax end
} // function ajax() end

$(function(){
	// 글쓰기 버튼 클릭
	$(".btn-info").click(function(){
		location.href="write";
	})
	
	// 줄보기 버튼 변경
	$("#viewcount").change(function(){
		go(1); // 줄보기 변경 시 보여줄 기본페이지는 1페이지
	})
}) // ready end
