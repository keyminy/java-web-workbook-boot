async function get1(bno){
	const result = await axios.get(`/replies/list/${bno}`);
	return result;
}

async function getList({bno,page,size,goLast}){
	const result = await axios.get(`/replies/list/${bno}`
															,{params:{page,size}});
	if(goLast){
		const total = result.data.total;
		const lastPage = parseInt(Math.ceil(total/size));
		return getList({bno:bno,page:lastPage,size:size});
	}
	return result.data;
}

/* 댓글 등록 post */
async function addReply(replyObj){
	const response = await axios.post(`/replies/`,replyObj);
	return response.data;
}

/* 댓글 한건 조회 */
async function getReply(rno){
	const response = await axios.get(`/replies/${rno}`);
	return response.data;
}

/* 댓글 수정 */
async function modifyReply(replyObj){
	const response = await axios.put(`/replies/${replyObj.rno}`,replyObj);
	return response.data;
}

/* 댓글 삭제 */
async function removeReply(rno){
	const response = await axios.delete(`/replies/${rno}`);
	return response.data;
}