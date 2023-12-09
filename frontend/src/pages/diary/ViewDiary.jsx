import axios from "axios";
import React, { useEffect,useState } from "react";
import { Link, useParams, useNavigate} from "react-router-dom";
import { RiDeleteBin6Line, RiEdit2Line } from 'react-icons/ri';
import { GetWithAuth, DeleteWithAuth } from "../../helpers/axios_helper";

export default function ViewDiary() {
   let navigate = useNavigate();
    const [diary, setDiary] = useState([]);
    const { id } = useParams();

    useEffect(() => {
        loadDiary();
    }, []);

    const loadDiary = async () => {
        const result = await GetWithAuth(`http://localhost:8080/api/v1/diary/get/${id}`);
        setDiary(result.data);
    }
    
  const deleteDiary = async id => {
    await DeleteWithAuth(`http://localhost:8080/api/v1/diary/delete/${id}`);
    navigate("/viewdiarys");
  }
  return (
    <div className="container">
      <header>
        <h1 className="text-center mt-4 text-dark-emphasis" >Diary</h1>
      </header>
      <div className="py-4">
      <div class="card" >
        <div class="card-body">
          <h5 class="card-title">{diary.title}</h5>
          <p class="card-text">{diary.content}</p>
          <hr />
          <Link
                    className="btn btn btn-outline-info mx-3"
                    to={`/editdiary/${diary.id}`}
                  >
                    <RiEdit2Line />
                  </Link>
                  <button
                    className="btn btn-outline-danger mx-3"
                    onClick={() => deleteDiary(diary.id)}
                  >
                    <RiDeleteBin6Line />
                  </button>

        </div>
      </div >
        <Link className="btn btn-outline-danger mx-2 mt-2" to="/viewdiarys">
          Back
        </Link>
      </div>
    </div>
  )
}
