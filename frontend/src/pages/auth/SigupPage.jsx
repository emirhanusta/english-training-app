import React, { useState } from 'react';
import { PostWithoutAuth,  } from "../../helpers/axios_helper";
import { useNavigate } from 'react-router-dom';

const SigninPage = () => {
  let navigate = useNavigate();
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    email: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };


  const handleSubmit = (e) => {
    e.preventDefault();
    const result = PostWithoutAuth('http://localhost:8080/api/v1/auth/signup', formData);
    result.then((res) => {
      console.log(res);
      navigate('/login');
    });
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card">
            <div className="card-body">
              <h2 className="card-title text-center">Sign Up</h2>
                <div class="form-floating mb-3">
                  <input type="text" class="form-control" name='username' placeholder="username" onChange={handleChange}/>
                  <label for="floatingInput">Username</label>
                </div>
                <div class="form-floating">
                  <input type="password" class="form-control" name='password' placeholder="Password" onChange={handleChange}/>
                  <label for="floatingPassword">Password</label>
                </div>
                <br></br>
                <div class="form-floating">
                  <input type="email" class="form-control" name='email' placeholder="Email" onChange={handleChange}/>
                  <label for="floatingPassword">Email</label>
                </div>
              <div className="form-group">
                <button className="btn btn-primary btn-block mt-3" onClick={handleSubmit}>
                  Sign Up
                </button>
               </div>
            </div>
          </div>
          Already have an account? <a href="/login">Log In</a>
        </div>
      </div>
    </div>
  );
};

export default SigninPage;
