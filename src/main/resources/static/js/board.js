document.addEventListener('DOMContentLoaded', function() {

    checkLoginStatus().then(response => {
        console.log(response);
        if (response.status === "loggedIn") {
            // User is logged in, generate the welcome message and update the UI
            document.getElementById('welcomeSection').textContent = `Welcome ${response.user.userId}`;
            document.getElementById('loginSection').style.display = 'none';
            document.getElementById('newPostForm').style.display = 'block';
            document.getElementById('logoutButton').style.display = 'block';
        } else {
            // User is not logged in, hide the welcome section and adjust the UI accordingly
            document.getElementById('welcomeSection').style.display = 'none';
            document.getElementById('loginSection').style.display = 'block';
            document.getElementById('newPostForm').style.display = 'none';
            document.getElementById('logoutButton').style.display = 'none';
        }
    });

    // Dummy function to simulate login status check
    // Replace this with a real check to your backend
    function checkLoginStatus() {
        return fetch('user/check-login')
            .then(response => response.json())
            .then(data => data)
            .catch(error => {
                console.error('Error checking login status:', error);
                return false;
            });
    }

    // Add event listener to login button
    document.getElementById('loginButton').addEventListener('click', function() {
        // Implement login logic or redirect to a login page
        window.location.href = '/login.html';
    });

    // Add event listener to logout button
    document.getElementById('logoutButton').addEventListener('click', function() {
        // Make an AJAX call to the logout endpoint
        fetch('/user/logout', {
            method: 'GET'
        })
            .then(response => {
                if (response.ok) {
                    // On successful logout, reload the current page
                    window.location.reload();
                } else {
                    throw new Error('Logout failed');
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    });


    fetch('/board/posts')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(posts => {
            const postsContainer = document.getElementById('posts');
            posts.forEach(post => {
                const postElement = document.createElement('div');
                postElement.innerHTML = `
                        <h3 class="post-title"><a href="post-detail.html?postId=${post.id}">${post.title}</a></h3>
                        <p>Posted by: ${post.userId}</p>
                        <p>Date: ${new Date(post.date).toLocaleString()}</p>
                        <p>viewCounts: ${post.viewCount}</p>
                    `;
                postsContainer.appendChild(postElement);
            });

        })
        .catch(error => {
            console.error('Error fetching posts:', error);
            postsContainer.innerHTML = '<p>Failed to load posts.</p>';
        });


});

