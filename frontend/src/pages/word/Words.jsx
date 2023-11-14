import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { IoArrowDown,IoArrowUp } from "react-icons/io5";

export default function Words() {
  const [words, setWords] = useState([]);
  const [filteredWords, setFilteredWords] = useState([]);
  const [levelFilter, setLevelFilter] = useState("");
  const [statusFilter, setStatusFilter] = useState("");
  const [currentPage, setCurrentPage] = useState(0);
  const [itemsPerPage, setItemsPerPage] = useState(10); 
  const [direction, setDirection] = useState("asc");
  const [sortField, setSortField] = useState("level");

  useEffect(() => {
    loadWords();
  }, [currentPage, levelFilter, statusFilter, sortField, direction]);

  const loadWords = async () => {
    const result = await axios.get(`http://localhost:8080/api/v1/word/getAllWithFilter?page=${currentPage}&size=${itemsPerPage}&level=${levelFilter}&status=${statusFilter}&sortField=${sortField}&direction=${direction}`);
    setWords(result.data);
  }
  
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

  const handleSortFieldChange = (event) => {
    setSortField(event.target.value);
  }

  const handleDirectionChange = (event) => {
    setDirection(event.target.value);
  }

  const clearFilters = () => {
    setLevelFilter("");
    setStatusFilter("");
    setSortField("name");
  }
  const paginate = (pageNumber) => setCurrentPage(pageNumber);

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
            <th> Sort</th>
            <td>
              <select className="form-select" value={sortField} onChange={handleSortFieldChange}>
                <option value="name">Name</option>
                <option value="level">Level</option>
                <option value="status">Status</option>
              </select>
            </td>
            <td>
              <div class="btn-group" role="group" aria-label="Button group with nested dropdown">
              <button className="btn btn-info btn-block" onClick={() => setDirection("desc")}> <IoArrowDown /></button>
              <button className="btn btn-info btn-block" onClick={() => setDirection("asc")}> <IoArrowUp /></button>
            </div>
            </td>
            <th className="col d-flex justify-content-end">
              <button className="btn btn-info btn-block" onClick={clearFilters}>
                Clear Filters
              </button>
            </th>
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
            <th>
              Definition
            </th>
            <th></th> <th></th><th></th>
              <th className="col d-flex justify-content-end">
                <Link className="btn btn-info " to={`/addWord/`}>
                  Add Word
                </Link>
              </th>
          </tr>
          {
            filteredWords.map((word, i) => (
              
              <tr key={i}>
                <th scope="row">{word.name}</th>
                <td>{word.level}</td>
                <td>{word.status}</td>
                <td>{word.definition}</td>
                <th></th> <th></th><th></th>
                <td className="col d-flex justify-content-end">
                <Link className="btn btn-info mx-2" to={`/viewworddetails/${word.id}`}>
                   View
              </Link>

                </td>
              </tr>
            ))
          }
        </tbody>
      </table>
    </div>
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
                  words.length < itemsPerPage
                }>
                Next
              </button>
            </li>
          </ul>
          </nav>
  </div>
  );
}
