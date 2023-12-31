import { GetWithAuth } from '../../helpers/axios_helper';
import React, { useEffect,useState } from "react";
import { Link, useParams } from "react-router-dom";

export default function ViewWordList() {
  const [wordList, setWordList] = useState(
    {
      name: "",
      words: []
    });

  const { id } = useParams();

  const loadWordList = async () => {
    const result = await GetWithAuth(`http://localhost:8080/api/v1/word-list/get/${id}`);
    setWordList(result.data);
  }

  useEffect(() => {
    loadWordList();
  }, []);


  return (
    <div className="container">
      <header>
        <h1 className="text-center mt-4 text-dark-emphasis" >Word List</h1>
      </header>
      <div className="py-4">
        <table className="table border shadow">
          <thead className="thead-dark">
            <tr>
              <th scope="col">{wordList.name}</th>
              <th scope="col">Words</th>

            </tr>
          </thead>
          <tbody>
            {wordList.words.map((word, i) => (
              <tr key={i}>
                <th scope="row">{i + 1}</th>
                <td>{word.name}</td>
                <td>
                <Link className="btn btn-outline-info mx-2" to={`/viewworddetails/${word.id}`}>
                 View
                </Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <Link className="btn btn-outline-danger mx-2" to="/viewwordlists">
          Back
        </Link>
      </div>
    </div>
  )
}
