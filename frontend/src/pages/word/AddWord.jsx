import { PostWithAuth } from '../../helpers/axios_helper';
import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { Dropdown } from 'react-bootstrap';

export default function AddWord() {
    let navigate = useNavigate();
    const { id } = useParams();

    const [word, setWord] = useState({
        name: "",
        definition: "",
        level: "Select level",
        exampleSentences: [],
        wordListId: id
    });

    const onExampleSentencesChange = (e) => {
      const sentences = e.target.value.split('\n'); 
      setWord({ ...word, exampleSentences: sentences });
    };

    const onInputChange = e => {
        setWord({ ...word, [e.target.name]: e.target.value });
    };

    const onSubmit = async e => {
      e.preventDefault();
      try {
        await PostWithAuth("http://localhost:8080/api/v1/word/save", word);
        navigate(`/`);
      } catch (error) {
        alert(error.response.data.message);
      }
    };
  return (
    <div className="container">
      <div className="row">
        <div className="col-md-12  border rounded p-4 mt-5 shadow">
          <h2 className="text-center m-4">Add Word</h2>

          <form onSubmit={onSubmit}>
            <div className="mb-3">
              <label htmlFor="name" className="form-label">
                Name
              </label>
              <input
                type="text"
                className="form-control"
                id="name"
                name="name"
                value={word.name}
                onChange={onInputChange}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="definition" className="form-label">
                Definition
              </label>
              <input
                type="text"
                className="form-control"
                id="definition"
                name="definition"
                value={word.definition}
                onChange={onInputChange}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="level" className="form-label">
                Level
              </label>
              <Dropdown>
                <Dropdown.Toggle variant="outline-info" id="dropdown-basic">
                  {word.level}
                </Dropdown.Toggle>
                <Dropdown.Menu>
                  <Dropdown.Item onClick={() => setWord({ ...word, level: "A1" })}>A1</Dropdown.Item>
                  <Dropdown.Item onClick={() => setWord({ ...word, level: "A2" })}>A2</Dropdown.Item>
                  <Dropdown.Item onClick={() => setWord({ ...word, level: "B1" })}>B1</Dropdown.Item>
                  <Dropdown.Item onClick={() => setWord({ ...word, level: "B2" })}>B2</Dropdown.Item>
                  <Dropdown.Item onClick={() => setWord({ ...word, level: "C1" })}>C1</Dropdown.Item>
                  <Dropdown.Item onClick={() => setWord({ ...word, level: "C2" })}>C2</Dropdown.Item>
                </Dropdown.Menu>
              </Dropdown>
            </div>
            <div className="mb-3">
              <label htmlFor="exampleSentences" className="form-label">
                Example Sentences
              </label>
              <textarea
                className="form-control"
                id="exampleSentences"
                name="exampleSentences"
                value={word.exampleSentences.join('\n')} 
                onChange={onExampleSentencesChange}
              />
            </div>
            <button type="submit" className="btn btn-info btn-block">
              {console.log(word)}
              Add
            </button>
            <Link className="btn btn-outline-danger mx-2" to={`/`}>
              Cancel
            </Link>
          </form>
        </div>
      </div>
    </div>
  )
}
