// FilterDropdowns.jsx
import React from 'react';
import { Dropdown, DropdownButton } from 'react-bootstrap';
import { IoArrowDown, IoArrowUp } from 'react-icons/io5';

const FilterDropdowns = (props) => {
  const {
    levelFilter,
    statusFilter,
    sortField,
    setLevelFilter,
    setStatusFilter,
    setSortField,
    setDirection,
    clearFilters,
  } = props;

  const stopPropagation = (e) => {
    e.stopPropagation();
  };

  return (
    <DropdownButton
      id="filter-dropdown"
      title="Filters"
      variant="info"
      className="btn-block"
    >
      <Dropdown.Item onClick={stopPropagation}>
        <label htmlFor="levelFilter">Level</label>
        <select
          id="levelFilter"
          className="form-select"
          value={levelFilter}
          onChange={(e) => setLevelFilter(e.target.value)}
        >
          <option value="">All</option>
          <option value="A1">A1</option>
          <option value="A2">A2</option>
          <option value="B1">B1</option>
          <option value="B2">B2</option>
          <option value="C1">C1</option>
          <option value="C2">C2</option>
        </select>
      </Dropdown.Item>

      <Dropdown.Item onClick={stopPropagation}>
        <label htmlFor="statusFilter">Status</label>
        <select
          id="statusFilter"
          className="form-select"
          value={statusFilter}
          onChange={(e) => setStatusFilter(e.target.value)}
        >
          <option value="">All</option>
          <option value="LEARNING">LEARNING</option>
          <option value="LEARNED">LEARNED</option>
        </select>
      </Dropdown.Item>

      <Dropdown.Item onClick={stopPropagation}>
        <label htmlFor="sortField">Sort</label>
        <select
          id="sortField"
          className="form-select"
          value={sortField}
          onChange={(e) => setSortField(e.target.value)}
        >
          <option value="name">Name</option>
          <option value="level">Level</option>
          <option value="status">Status</option>
        </select>
      </Dropdown.Item>

      <Dropdown.Item onClick={stopPropagation}>
        <label>Direction</label>
        <div className="btn-group" role="group" aria-label="Button group with nested dropdown">
          <button
            className="btn btn-info btn-block"
            onClick={() => setDirection("desc")}
          >
            <IoArrowDown />
          </button>
          <button
            className="btn btn-info btn-block"
            onClick={() => setDirection("asc")}
          >
            <IoArrowUp />
          </button>
        </div>
      </Dropdown.Item>

      <Dropdown.Item onClick={stopPropagation}>
        <button
          className="btn btn-info btn-block"
          onClick={clearFilters}
        >
          Clear Filters
        </button>
      </Dropdown.Item>
    </DropdownButton>
  );
};

export default FilterDropdowns;
