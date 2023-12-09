import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { PostWithAuth } from '../../helpers/axios_helper';

export default function AddWordList() {

  let navigate = useNavigate();

  const [wordList, setWordList] = useState({
    name: "",
    userId: localStorage.getItem('currentUser')
  });

  const { name, userId } = wordList;

  const onInputChange = e => {
    setWordList({ ...wordList, [e.target.name]: e.target.value });
  };

  const onSubmit = async e => {
    e.preventDefault();
    console.log(localStorage.getItem('token'));
    console.log(wordList);
    await PostWithAuth("http://localhost:8080/api/v1/word-list/save", wordList);
    navigate("/viewwordlists");
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-12  border rounded p-4 mt-5 shadow">
          <h2 className="text-center m-4">Add Word List</h2>
          <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
              <label htmlFor="Name" className="form-label">
                Name
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter word list name"
                name="name"
                value={name}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <button type="submit" className="btn btn-outline-info">
              Add
            </button>
            <Link className="btn btn-outline-danger mx-2" to="/viewwordlists">
              Cancel
            </Link>
          </form>
        </div>
      </div>
    </div>
  )
}
