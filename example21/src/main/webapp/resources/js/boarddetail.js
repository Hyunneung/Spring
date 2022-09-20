$(function(){
	$("#comment table").hide(); // 테이블 숨기기
	var page = 1; // 더보기에서 보여줄 페이지를 기억할 변수
	count = parseInt($("#count").text()); // 총 댓글 수
	
	if(count != 0) {// 댓글 수가 0이 아니면
		getCommentList(1); // 첫페이지 댓글리스트 구해온다
	} else { // 댓글이 없는 경우
		$("#commentMessage").text("등록된 댓글이 없습니다");
	}
	
	
	// 댓글 리스트 가져오는 ajax
	function getCommentList(currentPage) {
		// 헤더에 있는 값들 가져옴
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
		$.ajax({
			type : "post",
			url : "../comment/list",
			data : {"board_num" : $("#board_num").val(),
				    "page" : currentPage},
			beforeSend : function(xhr) { 
						// 403 Access deny 오류 처리(Spring Security CSRF) - 데이터를 전송하기 전에 헤더에 csrf값을 설정
			        	xhr.setRequestHeader(header, token);			
			    },
			dataType : "json",
			success : function(rdata){ 
				    	$("#count").text(rdata.listcount); // rdata.listcount: 총 댓글 수
				    	
				    	// 총 댓글 수가 0보다 큰 경우
				    	if(rdata.listcount > 0) {
				    		$("#comment table").show();
				    		$("#comment tbody").empty();
				    		$(rdata.commentList).each(function(){
				    			output = '';
				    			img = '';
				    			
				    			// 현재 로그인한 회원이 작성한 댓글인 경우
				    			if($("#loginid").text() == this.id) { // this.id: 댓글 작성자 아이디
				    				img = "<img src='../resources/image/pencil2.png' width='15px' class='update'>"
										+ "<img src='../resources/image/delete.png' width='15px' class='remove'>"
										+ "<input type='hidden' value='" + this.num + "' >"; // this.num: 댓글번호
				    			}
				    			output += "<tr>"
				    					+ "		<td>" + this.id + "</td>";
				    			output += "		<td></td>";
				    			output += "		<td>" + this.reg_date + img + "</td></tr>";
				    			
				    			$("#comment tbody").append(output);
				    			$("#comment tbody tr:last").find("td:nth-child(2)").text(this.content);
				    		}) // each end
				    		
				    		if(rdata.listcount > rdata.commentList.length) { // 총 댓글 수 > 보여준 댓글 수
				    			$("#commentMessage").text("더보기");
				    		} else {
				    			$("#commentMessage").text("");
				    		}
				    	} else {
				    		// 댓글이 없는 경우
				    		$("#commentMessage").text("등록된 댓글이 없습니다.");
				    		$("#comment tbody").empty();
				    		$("#comment table").hide();
				    	} // if-else end
				    } // success end
		}) // ajax end
	} // function getCommentList() end
	
	
	// 댓글 쓴 글자수만큼 카운트되기 (글자수 제한)
	$("#content").on("keyup", function(){
		var content = $(this).val();
		var length = content.length;
		
		if(length > 50) {
			length = 50;
			content = content.substring(0, length);
		}
		$('.float-left').text(length + "/50");
	}) // $("#content") 키업 end
	
	
	// 더보기 클릭
	$("#commentMessage").click(function(){
		getCommentList(++page);
	})
	
	
	// 등록 또는 수정완료 버튼 클릭
	$("#write").click(function(){
		var content = $("#content").val();
		
		// 댓글 내용이 없는 경우
		if(!content) {
			alert("내용을 입력하세요");
			return false;
		} else {
			var buttonText = $("#write").text(); // 버튼 기능
			$(".float-left").text("총 50자까지 가능합니다.");
			
			// 댓글 등록(add)하는 경우
			if(buttonText == "등록") {
				url = "../comment/add"; // 댓글 등록하는 주소
				data = {"content": content, // 댓글 등록 내용
						"id": $("#loginid").text(), // 댓글 등록하는 아이디
						"board_num": $("#board_num").val()}; // 댓글 등록할 게시글
			} else {
				// 댓글 수정(update)하는 경우
				url = "../comment/update"; // 댓글 수정하는 주소
				data = {"content": content, // 댓글 수정 내용
						"num": num}; // 수정할 댓글 번호
				$("#write").text("등록"); // 다시 등록으로 변경
				$("#comment .cancel").remove(); // 취소 버튼 삭제
			} // if-else end
			
			
			// 헤더에서 가져온 값
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			$.ajax({
				type: "post",
				url: url,
				data: data,
				beforeSend : function(xhr) { 
					// 403 Access deny 오류 처리(Spring Security CSRF) - 데이터를 전송하기 전에 헤더에 csrf값을 설정합니다.
		        	xhr.setRequestHeader(header, token);			
				},
				success: function(rdata){
					$("#comment").val('');
					
					// 댓글 등록 또는 수정 성공한 경우
					if(rdata == 1) {
						getCommentList(page);
					} // if end
				} // success end
			}) // ajax end
		}
		$("#content").val('');
	}) // $("#write").click() end
	
	
	// pencil2.png 클릭하는 경우 (댓글 수정)
	$("body").on("click", ".update", function(){
		var originalComm = $(this).parent().prev().text(); // 기존 댓글 내용
		$("#content").focus().val(originalComm); // 댓글 textarea에 수정할 기존 댓글 내용 기재됨
		num = $(this).next().next().val(); // 수정할 댓글 번호 (input hidden으로 저장해놓음)
		$("#write").text("수정완료"); // 버튼을 수정완료 버튼으로 바꿈
		
		if(!$("#write").prev().is(".cancel")) {
			$("#write").before('<button class="btn btn-danger float-right cancel">취소</button>');
		}
		
		$("#comment .update").parent().parent().not(this).css('background', 'white');
		$(this).parent().parent().css('background', 'lightgray'); // 수정할 행의 배경색을 변경합니다.
		$('.remove').prop("disabled", true); // [수정완료], [취소] 중에는 삭제를 클릭할 수 없도록 합니다.
	})
	// 취소버튼 클릭
	$("body").on("click", ".cancel", function(){
		$("#comment tr").css('background', 'white');
		$(this).remove(); // 취소버튼 사라짐
		$("#write").text("등록");
		$("#content").val(''); // 댓글 textarea를 공란으로
		$('.remove').prop("disabled", true); 
	})
	
	// delete.png를 클릭하는 경우 (댓글 삭제)
	$("body").on("click", ".remove", function(){
		if(!confirm("정말 삭제하시겠습니까?")) {
			return;
		}
		var deleteNum = $(this).next().val(); // 수정할 댓글 번호 (input hidden으로 저장해놓음)
		
		// 헤더에서 가져온 값
		var token  = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			type:"post",
			url:"../comment/delete",
			data: {"num":deleteNum},
			beforeSend : function(xhr) {
				// 403 Access deny 오류 처리(Spring Security CSRF)
				// 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
				xhr.setRequestHeader(header, token);
			},
			success:function(rdata){
				getCommentList(page);
				if(rdata == 0) {
					alert("댓글 삭제에 실패했습니다.");
				} else {
					alert("댓글이 삭제되었습니다.");
				} // if-else end
			} // success end
		}) // ajax end
	})
	
	
}) // ready end