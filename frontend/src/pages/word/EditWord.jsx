import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { Dropdown } from 'react-bootstrap';
import { GetWithoutAuth, PutWithAuth  } from "../../helpers/axios_helper";

export default function EditWord() {
    let navigate = useNavigate();
    
    const { id } = useParams();
    const [word, setWord] = useState({
        name: "",
        definition: "",
        level: "",
        status: "",
        exampleSentences: []
    });

    const onExampleSentencesChange = (e) => {
      const sentences = e.target.value.split('\n'); 
      setWord({ ...word, exampleSentences: sentences });
    };

    const onInputChange = e => {
        setWord({ ...word, [e.target.name]: e.target.value });
    };

    useEffect(() => {
        loadWord();
    }
    , []);

    const onSubmit = async e => {
        e.preventDefault();
        console.log(localStorage.getItem('token'));
        try {
        await PutWithAuth(`http://localhost:8080/api/v1/word/update/${id}`, word);
        navigate(`/viewworddetails/${id}`);
    
      } catch (error) {
        alert(error.response.data.message);
      }
    };

    const loadWord = async () => {
        const result = await GetWithoutAuth(`http://localhost:8080/api/v1/word/get/${id}`);
        setWord(result.data);
    }


  return (
    <div className="container">
      <div className="row">
        <div className="col-md-12  border rounded p-4 mt-5 shadow">
          <h2 className="text-center m-4">Edit Word</h2>

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
              <textarea
                className="form-control"
                id="definition"
                name="definition"
                rows="3"
                value={word.definition}
                onChange={onInputChange}
              ></textarea>
            </div>
            <div className="mb-3">
              <label htmlFor="level" className="form-label">
                Level
              </label>
              <Dropdown>
                <Dropdown.Toggle variant="info" id="dropdown-basic">
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
              <label htmlFor="status" className="form-label">
                Status
              </label>
              <Dropdown>
                <Dropdown.Toggle variant="info" id="dropdown-basic">
                  {word.status}
                </Dropdown.Toggle>
                <Dropdown.Menu>
                  <Dropdown.Item onClick={() => setWord({ ...word, status: "LEARNING" })}>LEARNING</Dropdown.Item>
                  <Dropdown.Item onClick={() => setWord({ ...word, status: "LEARNED" })}>LEARNED</Dropdown.Item>
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
                rows="3"
                value={word.exampleSentences.join('\n')} 
                onChange={onExampleSentencesChange}
              />
            </div>
            <button type="submit" className="btn btn-outline-info btn-block">
              Update
            </button>
          </form>
          <Link className="btn btn-outline-danger mx-2 mt-2" to={`/viewworddetails/${id}`}>
            Back
          </Link>
        </div>
      </div>
    </div>

  )
}
