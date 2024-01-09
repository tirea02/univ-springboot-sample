document.getElementById('imageUpload').addEventListener('change', function(event) {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onload = function(e) {
        const imagePreviewDiv = document.getElementById('imagePreview');
        imagePreviewDiv.innerHTML = `<img src="${e.target.result}" alt="Image Preview" style="max-width: 100%; height: auto;">`;
    };

    reader.readAsDataURL(file);
});

document.getElementById('createPostForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const title = document.getElementById('title').value;
    const content = document.getElementById('content').value;
    const imageInput = document.getElementById('imageUpload');
    const imageName = imageInput.files.length > 0 ? imageInput.files[0].name : '';

    // Create FormData object and append data
    const postDetails = {
        title: title,
        content: content,
        imgFile: imageName,
    };

    console.log(postDetails);

    fetch('/board/createPost', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(postDetails),
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('Network response was not ok.');
        })
        .then(data => {
            console.log('Post created:', data);
            window.location.href = '/board.html'; // Redirect after post creation
        })
        .catch(error => console.error('Error creating post:', error));
});
