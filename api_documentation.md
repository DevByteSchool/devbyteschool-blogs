# API Documentation.

### Entity and Business Logic:

1. **User**
2. **Blogs**
3. **Comments**

### A) Authentication & Authorization

#### 1) Register API
- **Endpoint:** `POST /api/v1/register`
- **Request Payload:**
  ```
  {
    "full_name": "John Doe",
    "email_id": "john@example.com",
    "password": "securepassword"
  }
  ```
- **Successful Response (HTTP Status Code: 201 Created):**
  ```
  {
    "message": "Registration successful",
    "access_token": "your_access_token_here"
  }
  ```
- **Failed Response (HTTP Status Code: 400 Bad Request):**
  ```
  {
    "error": "Registration failed",
    "message": "Email already exists"
  }
  ```

#### 2) Login
- **Endpoint:** `POST /api/v1/login`
- **Request Payload:**
  ```
  {
    "email_id": "john@example.com",
    "password": "securepassword"
  }
  ```
- **Successful Response (HTTP Status Code: 200 OK):**
  ```
  {
    "message": "Login successful",
    "user_id": 123,
    "access_token": "your_access_token_here contain all information"
  }
  ```
- **Failed Response (HTTP Status Code: 401 Unauthorized):**
  ```
  {
    "error": "Login failed",
    "message": "Invalid credentials"
  }
  ```

### B) Blog

#### 1) Create Blog
- **Endpoint:** `POST /api/v1/blogs`
- **Request Payload:**
  ```
  {
    "user_id": 123,
    "title": "My First Blog",
    "description": "This is the content of my blog.",
    "url": "https://devbyteschool.com/my-first-blog"
  }
  ```
- **Successful Response (HTTP Status Code: 201 Created):**
  ```
  {
    "message": "Blog created successfully",
    "blog_id": 456
  }
  ```
- **Failed Response (HTTP Status Code: 404 Not Found):**
  ```
  {
    "error": "Blog creation failed",
    "message": "Invalid user"
  }
  ```

#### 2) Update Blog
- **Endpoint:** `PUT /api/v1/blogs/:blog_id`
- **Request Payload:**
  ```
  {
    "title": "Updated Blog Title",
    "description": "Updated content of the blog.",
    "url": "https://devbyteschool.com/updated-blog"
  }
  ```
- **Successful Response (HTTP Status Code: 200 OK):**
  ```
  {
    "message": "Blog updated successfully"
  }
  ```
- **Failed Response (HTTP Status Code: 404 Not Found):**
  ```
  {
    "error": "Blog update failed",
    "message": "Blog not found"
  }
  ```

#### 3) Delete Blog
- **Endpoint:** `DELETE /api/v1/blogs/:blog_id`
- **Successful Response (HTTP Status Code: 200 OK):**
  ```
  {
    "message": "Blog deleted successfully"
  }
  ```
- **Failed Response (HTTP Status Code: 404 Not Found):**
  ```
  {
    "error": "Blog deletion failed",
    "message": "Blog not found"
  }
  ```

#### 4) Publish Blog
- **Endpoint:** `PUT /api/v1/blogs/:blog_id/publish`
- **Successful Response (HTTP Status Code: 200 OK):**
  ```
  {
    "message": "Blog published successfully"
  }
  ```
- **Failed Response (HTTP Status Code: 404 Not Found):**
  ```
  {
    "error": "Blog publishing failed",
    "message": "Blog not found"
  }
  ```

### C) Comment

#### 1) Add Comment
- **Endpoint:** `POST /api/v1/comments`
- **Request Payload:**
  ```
  {
    "blog_id": 456,
    "question": "Great blog! Can you provide more details on this topic?"
  }
  ```
- **Successful Response (HTTP Status Code: 201 Created):**
  ```
  {
    "message": "Comment added successfully",
    "comment_id": 789
  }
  ```
- **Failed Response (HTTP Status Code: 404 Not Found):**
  ```
  {
    "error": "Comment addition failed",
    "message": "Blog not found"
  }
  ```

#### 2) Update Comment
- **Endpoint:** `PUT /api/v1/comments/:comment_id`
- **Request Payload:**
  ```
  {
    "question": "I've changed my mind, disregard my previous comment."
  }
  ```
- **Successful Response (HTTP Status Code: 200 OK):**
  ```
  {
    "message": "Comment updated successfully"
  }
  ```
- **Failed Response (HTTP Status Code: 404 Not Found):**
  ```
  {
    "error": "Comment update failed",
    "message": "Comment not found"
  }
  ```

#### 3) Delete Comment
- **Endpoint:** `DELETE /api/v1/comments/:comment_id`
- **Successful Response (HTTP Status Code: 200 OK):**
  ```
  {
    "message": "Comment deleted successfully"
  }
  ```
- **Failed Response (HTTP Status Code: 404 Not Found):**
  ```
  {
    "error": "Comment deletion failed",
    "message": "Comment not found"
  }
  ```
