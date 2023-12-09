// Words.jsx

import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { IoArrowDown, IoArrowUp } from "react-icons/io5";
import { GetWithoutAuth } from "../../helpers/axios_helper";
import FilterDropdowns from "../../helpers/FilterDropdowns";

export default function Words() {
  const [words, setWords] = useState([]);
  const [filteredWords, setFilteredWords] = useState([]);
  const [levelFilter, setLevelFilter] = useState("");
  const [statusFilter, setStatusFilter] = useState("");
  const [currentPage, setCurrentPage] = useState(0);
  const [itemsPerPage, setItemsPerPage] = useState(3);
  const [direction, setDirection] = useState("asc");
  const [sortField, setSortField] = useState("level");
  const [searchName, setSearchName] = useState(" ");
  let disabled = localStorage.getItem('role') === "ADMIN" ? false : true;

  const handleSearchNameChange = (e) => {
    setSearchName(e.target.value);
  };

  const searchByName = async () => {
    const result = await GetWithoutAuth(`http://localhost:8080/api/v1/word/searchWithName/${searchName}`);
    setFilteredWords(result.data);
  };

  useEffect(() => {
    loadWords();
  }, [currentPage, levelFilter, statusFilter, sortField, direction]);

  const loadWords = async () => {
    const result = await GetWithoutAuth(`http://localhost:8080/api/v1/word/getAllWithFilter?page=${currentPage}&size=${itemsPerPage}&level=${levelFilter}&status=${statusFilter}&sortField=${sortField}&direction=${direction}`);
    setWords(result.data);
  }

  useEffect(() => {
    setFilteredWords(words);
  }, [words]);

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
        <h1 className="text-center mt-4 text-dark-emphasis">Words</h1>
      </header>

      <div className="py-4">
        <div className="container d-flex align-items-center justify-content-between mb-3">
          <div className="d-flex align-items-end" style={{ maxWidth: '300px' }}>
            <input
              type="text"
              className="form-control"
              placeholder="Enter word to search"
              onChange={(e) => handleSearchNameChange(e)}
            />
            <button className="btn btn-info ms-2" onClick={searchByName}>
              Search
            </button>
          </div>
          <FilterDropdowns
            levelFilter={levelFilter}
            statusFilter={statusFilter}
            sortField={sortField}
            setLevelFilter={setLevelFilter}
            setStatusFilter={setStatusFilter}
            setSortField={setSortField}
            setDirection={setDirection}
            clearFilters={clearFilters}
          />
        </div>

        <div className="py-4">
  <div className="row row-cols-1 row-cols-md-3 g-4">
    {filteredWords.map((word, i) => (
      <Link key={i} to={`/viewworddetails/${word.id}`} className="col text-decoration-none">
        <div className="card  mb-3">
          <div className="card-header">{word.name}</div>
          <div className="card-body d-flex flex-column align-items-center">
            <h5 className="card-title"></h5>
            <p className="card-text">
              <strong>Level:</strong> {word.level}<br />
            </p>
          </div>
        </div>
      </Link>
    ))}
  </div>
</div>
      </div>

      {words.length === itemsPerPage && currentPage !== 0 ? (
        <nav>
          <ul className="pagination justify-content-center">
            <li className="page-item">
              <button className="page-link" onClick={() => paginate(currentPage - 1)}>
                Previous
              </button>
            </li>
            <li className="page-item">
              <button className="page-link" onClick={() => paginate(currentPage + 1)}>
                Next
              </button>
            </li>
          </ul>
        </nav>
      ) : null}
    </div>
  );
}
