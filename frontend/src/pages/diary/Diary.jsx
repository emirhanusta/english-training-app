import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { GetWithAuth  } from "../../helpers/axios_helper";

export default function Diary() {
  const [diary, setDiary] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [itemsPerPage, setItemsPerPage] = useState(5); 
  const userId = localStorage.getItem('currentUser');
  
  useEffect(() => {
    loadDiary();
  }, [currentPage]);

  const loadDiary = async () => {
    const result = await GetWithAuth(`http://localhost:8080/api/v1/diary/getAll/${userId}?page=${currentPage}&size=${itemsPerPage}`);
    setDiary(result.data);
  }

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  return (
    <div className="container">
      <header>
        <h1 className="text-center mt-4 text-dark-emphasis" >Diary</h1>
      </header>
      <div className="py-4">
        <table className="table border shadow">
          <tbody>
            {
              (
                diary.length === 0 && currentPage !== 0
              ) ? (
                <tr>
                  <th scope="row">NO DATA</th>
                </tr>
              ) : (
                <tr>
                                    <th>
                    Name
                  </th>
                  <td >
                  </td>
                  <td className="col d-flex justify-content-end">
                    <Link 
                      className="btn btn-info btn-block"
                      to="/adddiary"
                    >
                     Add New
                    </Link>
                  </td>

            </tr>
              )
            }
            {diary.map((diary, index) => (
              <>
              <tr key={index}>
                <th>{diary.title}</th>
                <td></td>
                <td className="col d-flex justify-content-end">
                  <Link
                    className="btn btn btn-info mx-3"
                    to={`/viewdiarydetails/${diary.id}`}
                  >
                    View
                  </Link>
                </td>
              </tr>
              </>
            ))}
          </tbody>
        </table>
        <nav>
          <ul className="pagination justify-content-center">
            <li className="page-item">
              <button className="page-link" onClick={() =>
                paginate(currentPage - 1)} disabled={currentPage === 0
                }>
                Previous
              </button>
            </li>
            <li className="page-item">
              <button className="page-link" onClick={() =>
                paginate(currentPage + 1)} disabled={
                  diary.length != itemsPerPage
                }>
                Next
              </button>
            </li>
          </ul>
        </nav>
          <Link className="btn btn-outline-danger mx-2" to="/">
            Back
          </Link>
      </div>
    </div>
  )
}
