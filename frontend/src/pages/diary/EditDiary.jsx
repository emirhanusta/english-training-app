import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, Route, useNavigate, useParams } from "react-router-dom";
import { PutWithAuth, GetWithAuth} from "../../helpers/axios_helper";

export default function EditDiary() {
    let navigate = useNavigate();
    const { id } = useParams();
    const [diary, setDiary] = useState({
        title: "",
        content: "",
    });

    const { title, content } = diary;

    const onInputChange = e => {
        setDiary({ ...diary, [e.target.name]: e.target.value });
    };

    useEffect(() => {
        loadDiary();
    }
    , []);

    const onSubmit = async e => {
        e.preventDefault();
        await PutWithAuth(`http://localhost:8080/api/v1/diary/update/${id}`, diary);
        navigate(`/viewdiarydetails/${diary.id}`);
    }

    const loadDiary = async () => {
        const result = await GetWithAuth(`http://localhost:8080/api/v1/diary/get/${id}`);
        setDiary(result.data);
    }


  return (
    <div className="container">
      <div className="row">
        <div className="col-md-12  border rounded p-4 mt-5 shadow">
          <h2 className="text-center m-4">Edit Diary</h2>

          <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
              <label htmlFor="title" className="form-label">
                Title
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter diary title"
                name="title"
                value={title}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="content" className="form-label">
                Content
              </label>
              <textarea
                className="form-control"
                placeholder="Enter diary content"
                name="content"
                value={content}
                style={{ height: 400 }}
                onChange={(e) => onInputChange(e)}
                />
            </div>
            <button type="submit" className="btn btn-outline-info">
              Update
            </button>
            <Link className="btn btn-outline-danger mx-2" to="/viewdiarys">
              Cancel
            </Link>
          </form>
        </div>
      </div>
    </div>
  )
}
