import React, { useEffect, useState } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import { RiDeleteBin6Line, RiEdit2Line } from 'react-icons/ri';
import { GetWithoutAuth, PutWithAuth, DeleteWithAuth} from "../../helpers/axios_helper";

export default function ViewWord() {
    let navigate = useNavigate();
    const [name, setName] = useState("");
    const [word, setWord] = useState({
        name: "",
        definition: "",
        level: "",
        status: "",
        exampleSentences: [],
        wordListsId: ""
    });    
    const [inputVisible, setInputVisible] = useState(false);
    const [inputValue, setInputValue] = useState('');

    let isAdmin = localStorage.getItem('role') === "ADMIN" ? true : false;
    let isUser = localStorage.getItem('role') === "USER" ? true : false;

    const handleAddField = () => {
      setInputVisible(true);
    };

    const handleSubmit = () => {
        PutWithAuth(`http://localhost:8080/api/v1/word-list/addWord/${inputValue}/${word.id}`);
        setInputVisible(false);
        alert("Word added to the list");
      };

    const { id } = useParams();

    const loadWord = async () => {
        const res = await GetWithoutAuth(`http://localhost:8080/api/v1/word/get/${id}`);
        setWord(res.data);
    };

    useEffect(() => {
        loadWord();
    }, []);
    
    const deleteWord = async id => {
        await DeleteWithAuth(`http://localhost:8080/api/v1/word/delete/${id}`);
        navigate(`/`);
    }

  return (
    <div className="container">
        <div className="card border-secondary mb-3 mt-3" >
        <div className="card-header text-danger fs-3">{word.name}</div>
        <p className="card-text text-info fs-5 mb-1 mt-2"> {word.level} -- {word.status}</p>
            <div className="card-body text-success">
            <h5 className="card-title fs-4 mb-3">{word.definition}</h5>
            <ul className="list-group">
                <p className="card-text text-dark fs-5 mb-4 mt-1">Example Sentences:</p>
                {word.exampleSentences.map((sentence, i) => ( 
                    <li className="list-group-item blockquote-footer " key={i}>{sentence}</li>
                ))}
            </ul>
            {isAdmin ?<Link className="btn btn-outline-info mx-2" to={`/editword/${word.id}`}>
                <RiEdit2Line />
            </Link> : <div></div>}

            {isUser || isAdmin ? <button className="btn btn-outline-info mx-2" onClick={handleAddField}>
                Add Word List
            </button>: <div></div>}

            {isAdmin ? <button className="btn btn-outline-danger mx-2" onClick={() => deleteWord(word.id)}>
                <RiDeleteBin6Line />
            </button> : <div></div>}

            {inputVisible && (
                        <div className="mt-3">
                        <input
                          type="text"
                          className="form-control"
                          placeholder="Input Alanı"
                          value={inputValue}
                          onChange={(e) => setInputValue(e.target.value)}
                        />
                        <button className="btn btn-info mt-2" onClick={handleSubmit}>
                          Add
                        </button>
                      </div>
            )}
        </div>
        </div>
        <Link className="btn btn-outline-danger mx-2 mt-2" to={`/`}>
            Back
        </Link>
    </div>
  )
}
