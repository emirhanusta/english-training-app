import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Dropdown } from 'react-bootstrap';
import { DeleteWithAuth } from "../helpers/axios_helper";

const Navbar = () => {
  const navigate = useNavigate();
  const isLogged = localStorage.getItem('currentUser') !== null ? false : true;
  const userId = localStorage.getItem('currentUser');

  const deleteUser = (e) => {
    e.preventDefault();
    DeleteWithAuth(`http://localhost:8080/api/v1/user/delete/${localStorage.getItem('currentUser')}`);
    handleLogout(e);
    navigate('/');
  };

  const handleLogout = (e) => {
    e.preventDefault();
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('currentUser');
    localStorage.removeItem('role');
    
    const currentPath = window.location.pathname;
    if (currentPath === '/') {
      window.location.reload();
    } else {
      navigate('/', { replace: true });
    }
  };
  

  return (
    <div>
      <nav className="navbar navbar-expand-lg navbar-dark bg-secondary">
        <div className="container-fluid">
          <Link className="navbar-brand" to="/">
            English Training
          </Link>

          <button
            className="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>

          <div className="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul className="navbar-nav">
              <li className="nav-item">
                <Dropdown>
                  <Dropdown.Toggle variant="secondary" id="dropdown-basic">
                    {!isLogged ? localStorage.getItem('username') : "Login/Signup"}
                  </Dropdown.Toggle>
                  <Dropdown.Menu>
                    {isLogged ? (
                      <>
                        <Dropdown.Item as={Link} to="/login">
                          Login
                        </Dropdown.Item>
                        <Dropdown.Divider />
                        <Dropdown.Item as={Link} to="/signup">
                          Signup
                        </Dropdown.Item>
                      </>
                    ) : null}
                    {!isLogged ? (
                      <>
                      <Dropdown.Item onClick={handleLogout}>Logout</Dropdown.Item>
                      <Dropdown.Divider />
                      <Dropdown.Item as={Link} to="/viewdiarys">My Diarys</Dropdown.Item>
                      <Dropdown.Divider />
                      <Dropdown.Item as={Link} to="/viewwordlists">My Word Lists</Dropdown.Item>
                      <Dropdown.Divider />
                      <Dropdown.Item onClick={deleteUser}>Delete Account</Dropdown.Item>
                      </>
                    ) : null}
                    {localStorage.getItem('role') === "ADMIN" ? (
                      <>
                      <Dropdown.Divider />
                      <Dropdown.Item as={Link} to="/addword">Add New Word</Dropdown.Item>
                      </>
                    ) : null}
                  </Dropdown.Menu>
                </Dropdown>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </div>
  );
};

export default Navbar;
