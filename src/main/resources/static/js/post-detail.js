

let postId;


document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    postId = urlParams.get('postId'); // Get the post ID from the query string

    fetch(`/board/posts/${postId}`)
        .then(response => response.json())
        .then(post => {
            const postDetailDiv = document.getElementById('postDetail');
            postDetailDiv.innerHTML = `
                <h2 class="post-title">${post.title}</h2>
                <img src="img/${post.imgFile}" alt="Post Image" class="post-image">
                <p class="post-content">${post.content}</p>
                <p class="post-info">Posted by ${post.userId} on ${new Date(post.date).toLocaleString()}</p>
            `;
        })
        .catch(error => {
            console.error('Error fetching post details:', error);
            postDetailDiv.innerHTML = '<p>Error loading post details.</p>';
        });


    //check login status and decide display of reply form
     checkLoginStatus();
     fetchReplies(postId);



});

function checkLoginStatus() {
    fetch('user/check-login')
        .then(response => response.json())
        .then(data => {
            if (data.status === 'loggedIn') {
                showReplyForm(data.user);
            } else {
                hideReplyForm();
            }
        })
        .catch(error => console.error('Error:', error));
}

function showReplyForm(user) {
    // Logic to show the reply form
    document.getElementById("replyForm").style.display="block";
}

function hideReplyForm() {
    // Logic to hide the reply form or display a login prompt
    // i set default display as none so now this function is not needed
    // but u can add logic as u want if need
}

function submitReply() {
    const content = document.getElementById('replyContent').value;
    fetch(`board/posts/${postId}/replies`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(content),
    })
        .then(response => {
            if (response.ok) {
                console.log(`submit successful`)
            } else {
                // Handle errors
            }
        })
        .catch(error => console.error('Error:', error));
}

function fetchReplies(postId) {
    fetch(`board/posts/${postId}/getReplies`)
        .then(response => response.json())
        .then(replies => {
            const repliesListDiv = document.getElementById('repliesList');
            replies.forEach(reply => {
                const replyDiv = document.createElement('div');
                replyDiv.className = 'reply';
                replyDiv.innerHTML = `
                    <p>${reply.content}</p>
                    <small>Posted by User ${reply.userId} on ${new Date(reply.date).toLocaleString()}</small>
                `;
                repliesListDiv.appendChild(replyDiv);
            });
        })
        .catch(error => console.error('Error fetching replies:', error));
}