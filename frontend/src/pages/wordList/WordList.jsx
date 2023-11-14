import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link, useParams } from "react-router-dom";
import { RiDeleteBin6Line, RiEdit2Line } from 'react-icons/ri';

export default function WordList() {
  const [wordList, setWordList] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [itemsPerPage, setItemsPerPage] = useState(5); // Her sayfada kaç öğe gösterileceğini belirleyin

  const { id } = useParams();

  useEffect(() => {
    loadWordList();
  }, [currentPage]);

  const loadWordList = async () => {
    const result = await axios.get(`http://localhost:8080/api/v1/word-list/getAll?page=${currentPage}&size=${itemsPerPage}`);
    setWordList(result.data);
  }

  const deleteWordList = async id => {
    await axios.delete(`http://localhost:8080/api/v1/word-list/delete/${id}`);
    loadWordList();
  }

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  return (
    <div className="container">
      <header>
        <h1 className="text-center mt-4 text-dark-emphasis" >Word Lists</h1>
      </header>
      <div className="py-4">
        <table className="table border shadow">
          <tbody>
            {
              (
                wordList.length === 0 && currentPage !== 0
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
                      to="/addWordList"
                    >
                     Add New
                    </Link>
                  </td>
            </tr>
              )
            }
            {wordList.map((wordList) => (   
              <tr >
                <th>{wordList.name}</th>
                <td >
                  </td>
                <td className="col d-flex justify-content-end">
                  <Link
                    className="btn btn btn-info mx-3"
                    to={`/viewWordList/${wordList.id}`}
                  >
                    View
                  </Link>
                  <Link
                    className="btn btn btn-outline-info mx-3"
                    to={`/editWordList/${wordList.id}`}
                  >
                    <RiEdit2Line />
                  </Link>
                  <button
                    className="btn btn-outline-danger mx-3"
                    onClick={() => deleteWordList(wordList.id)}
                  >
                    <RiDeleteBin6Line />
                  </button>
                </td>
              </tr>
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
                  wordList.length < itemsPerPage
                }>
                Next
              </button>
            </li>
          </ul>
          <Link className="btn btn-outline-danger mx-2" to="/">
            Back
          </Link>
        </nav>
      </div>
    </div>
  )
}
