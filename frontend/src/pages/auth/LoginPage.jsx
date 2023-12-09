import React, { useState } from 'react';
import { PostWithoutAuth } from '../../helpers/axios_helper';
import { useNavigate } from 'react-router-dom';

const SigninPage = () => {
  let navigate = useNavigate();
  const [formData, setFormData] = useState({
    username: '',
    password: '',
  });
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleLogin = (e) => {
    e.preventDefault();
    const result = PostWithoutAuth('http://localhost:8080/api/v1/auth/login', formData);
    console.log(result);
    result.then((res) => {
      localStorage.setItem('token', res.data.token);
      localStorage.setItem('username', res.data.user.username);
      localStorage.setItem('currentUser', res.data.user.id);
      localStorage.setItem('role', res.data.user.role);
      navigate('/');
    });
  };



  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card">
            <div className="card-body">
              <h2 className="card-title text-center">Log In</h2>
                <div class="form-floating mb-3">
                  <input type="text" class="form-control" name='username' placeholder="username" onChange={handleChange}/>
                  <label for="floatingInput">Username</label>
                </div>
                <div class="form-floating">
                  <input type="password" class="form-control" name='password' placeholder="Password" onChange={handleChange}/>
                  <label for="floatingPassword">Password</label>
                </div>
              <div className="form-group">
                <button className="btn btn-primary btn-block mt-3" onClick={handleLogin}>
                  Log In
                </button>
               </div>
            </div>
          </div>
          Don't you have an account yet? <a href="/signup">Sign Up</a>
        </div>
      </div>
    </div>
  );
};

export default SigninPage;
