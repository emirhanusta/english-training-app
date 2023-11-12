import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

export default function Words() {
  const [words, setWords] = useState([]);
  const [filteredWords, setFilteredWords] = useState([]);
  const [levelFilter, setLevelFilter] = useState("");
  const [statusFilter, setStatusFilter] = useState("");

  const loadWords = async () => {
    const result = await axios.get(`http://localhost:8080/api/v1/word/getAllWithFilter?level=${levelFilter}&status=${statusFilter}`);
    setWords(result.data);
  }
  
  useEffect(() => {
    loadWords();
  }
  , [levelFilter, statusFilter]);

  useEffect(() => {
    setFilteredWords(words);
  }
  , [words]);

  const handleLevelFilterChange = (event) => {
    setLevelFilter(event.target.value);
  }

  const handleStatusFilterChange = (event) => {
    setStatusFilter(event.target.value);
  }

  const clearFilters = () => {
    setLevelFilter("");
    setStatusFilter("");
  }

  return (
    <div className="container">
      <header>
        <h1 className="text-center mt-4 text-dark-emphasis" >Words</h1>
      </header>
      <div className="py-4">
        <table className="table border shadow">
          <tbody>
            <tr>
              <th>
                Level
              </th>
              <td>
                <select className="form-select" value={levelFilter} onChange={handleLevelFilterChange}>
                  <option value="">All</option>
                  <option value="A1">A1</option>
                  <option value="A2">A2</option>
                  <option value="B1">B1</option>
                  <option value="B2">B2</option>
                  <option value="C1">C1</option>
                  <option value="C2">C2</option>
                </select>
              </td>
              <th>
                Status
              </th>
              <td>
                <select className="form-select" value={statusFilter} onChange={handleStatusFilterChange}>
                  <option value="">All</option>
                  <option value="LEARNING">LEARNING</option>
                  <option value="LEARNED">LEARNED</option>
                </select>
              </td>
            </tr>
            <tr>
              <th>
                Name
              </th>
              <th>
                Level
              </th>
              <th>
                Status
              </th>
              <th className="col d-flex justify-content-end">
                <button className="btn btn-secondary btn-block" onClick={clearFilters}>
                  Clear Filters
                </button>
              </th>
            </tr>
            {
              filteredWords.map((word, i) => (
                
                <tr key={i}>
                  <th scope="row">{word.name}</th>
                  <td>{word.level}</td>
                  <td>{word.status}</td>
                  <td className="col d-flex justify-content-end">
                    <Link 
                      className="btn btn-outline-secondary mx-2"
                      to={`/viewWordList/${word.wordListId}`}
                    >
                      View in Word List
                    </Link>

                  </td>
                </tr>
              ))
            }
          </tbody>
        </table>
      </div>
    </div>
  );
}
