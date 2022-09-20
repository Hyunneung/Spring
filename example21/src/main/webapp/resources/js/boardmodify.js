$(function(){
	var check = 0;
	
	// 수정 폼 제출
	$("form").submit(function(){
		if($.trim($("#board_subject").val()) == '') {
			alert("제목을 입력하세요");
			$("#board_subject").focus();
			return false;
		}
		if($.trim($("#board_content").val()) == '') {
			alert("내용을 입력하세요");
			$("#board_content").focus();
			return false;
		}
		if($.trim($("#board_pass").val()) == '') {
			alert("비밀번호를 입력하세요");
			$("#board_pass").focus();
			return false;
		}
		
		
		// 파일첨부 변경하지 않았을 때는 파일명을 추가해 전송
		if(check == 0) {
			value = $("#upfilename").text();
			html = "<input type='hidden' value='" + value + "' name='check'>";
			$(this).append(html);
		}
	}); // submit end
	
	
	// remove.png 유무
	function show() {
		// 파일 이름 있을 때만 x(파일삭제) 이미지 보이게 하기
		console.log($("#upfilename").text());
		if( $("#upfilename").text() == "" ) {
			$(".remove").css('display', 'none');
		} else {
			$(".remove").css({'display':'inline-block',
							  'position':'relative',
							  'top':'-5px'});
		} // if-else 끝
	} // function show() end
	show();
	
	
	// 파일명 나올 때 경로 제외한 파일명만 나오게 한다.
	$("#upfile").change(function(){ // input type=file 변경되면 실행되는 함수
		check++;
		var inputfile = $(this).val().split('\\');
		$("#upfilename").text(inputfile[inputfile.length - 1]);
		show();
	})
	
	
	// remove.png 이미지(파일삭제) 클릭하면 파일명 '' 으로 변경하고 remove 이미지 보이지 않게 함
	$(".remove").click(function(){
		$("#upfilename").text('');
		$(this).css('display', 'none');
	})
	
	
}) //ready end


