import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

export default function EditwordList() {
    let navigate = useNavigate();
    const { id } = useParams();
    const [wordList, setWordList] = useState({
        name: "",
    });

    const { name } = wordList;
    const onInputChange = e => {
        setWordList({ ...wordList, [e.target.name]: e.target.value });
    };

    useEffect(() => {
        loadWordList();
    }
    , []);

    const onSubmit = async e => {
        e.preventDefault();
        await axios.put(`http://localhost:8080/api/v1/word-list/update/${id}`, wordList);
        navigate("/");
    }

    const loadWordList = async () => {
        const result = await axios.get(`http://localhost:8080/api/v1/word-list/get/${id}`);
        setWordList(result.data);
    }


  return (
    <div className="container">
      <div className="row">
        <div className="col-md-12  border rounded p-4 mt-5 shadow">
          <h2 className="text-center m-4">Edit Word List</h2>

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
            <button type="submit" className="btn btn-outline-primary">
              Update
            </button>
            <Link className="btn btn-outline-danger mx-2" to="/">
              Cancel
            </Link>
          </form>
        </div>
      </div>
    </div>
  )
}
