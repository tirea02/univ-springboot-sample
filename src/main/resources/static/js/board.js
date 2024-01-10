window.addEventListener("pageshow", function (event) {
    if (event.persisted || (window.performance && window.performance.navigation.type === 2)) {
        // The page was loaded from the cache
        window.location.reload();
    }
});

document.addEventListener('DOMContentLoaded', function() {



    checkLoginStatus().then(response => {
        console.log(response);
        if (response.status === "loggedIn") {
            // User is logged in, generate the welcome message and update the UI
            document.getElementById('welcomeSection').textContent = `Welcome ${response.user.userId}`;
            document.getElementById('loginSection').style.display = 'none';
            document.getElementById('logoutButton').style.display = 'block';
            document.getElementById('newPostButton').style.display = 'block';

        } else {
            // User is not logged in, hide the welcome section and adjust the UI accordingly
            document.getElementById('welcomeSection').style.display = 'none';
            document.getElementById('loginSection').style.display = 'block';
            document.getElementById('logoutButton').style.display = 'none';
            document.getElementById('newPostButton').style.display = 'none';

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

    document.getElementById('newPostButton').addEventListener('click', function() {
        window.location.href = '/create-post.html'; // Redirect to the post creation page
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


    const postsPerPage = 5;
    let currentPage = 1;

    fetch('/board/posts')
        .then(response => response.json())
        .then(data => {
            // Sort posts by date in descending order (newest first)
            let  allPosts = data.sort((a, b) => new Date(b.date) - new Date(a.date));
            displayPosts(allPosts, currentPage, postsPerPage);
            // Create pagination buttons
            createPaginationButtons(allPosts.length, postsPerPage, allPosts);
        })
        .catch(error => console.error('Error fetching posts:', error));

    function displayPosts(posts, page, postsPerPage) {
        const startIndex = (page - 1) * postsPerPage;
        const selectedPosts = posts.slice(startIndex, startIndex + postsPerPage);

        const postsContainer = document.getElementById('posts');
        postsContainer.innerHTML = '';
        selectedPosts.forEach(post => {
            const postElement = document.createElement('div');
            postElement.innerHTML = `
                <h3 class="post-title"><a href="post-detail.html?postId=${post.id}">${post.title}</a></h3>
                <p>Posted by: ${post.userId}</p>
                <p>Date: ${new Date(post.date).toLocaleString()}</p>
                <p>viewCounts: ${post.viewCount}</p>
            `;
            postsContainer.appendChild(postElement);
        });
    }

    function createPaginationButtons(totalPosts, postsPerPage, allPosts) {
        const pageCount = Math.ceil(totalPosts / postsPerPage);
        const paginationContainer = document.getElementById('pagination');
        paginationContainer.innerHTML = '';

        for (let i = 1; i <= pageCount; i++) {
            const button = document.createElement('button');
            button.innerText = i;
            button.addEventListener('click', () => {
                currentPage = i;
                displayPosts(allPosts, currentPage, postsPerPage);
            });
            paginationContainer.appendChild(button);
        }
    }




});

