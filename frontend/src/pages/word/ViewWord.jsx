import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link, useParams, useNavigate } from "react-router-dom";
import { RiDeleteBin6Line, RiEdit2Line } from 'react-icons/ri';

export default function ViewWord() {
    let navigate = useNavigate();
    const [word, setWord] = useState({
        name: "",
        definition: "",
        level: "",
        status: "",
        exampleSentences: [],
        wordListsId: ""
    });    
    const { id } = useParams();

    const loadWord = async () => {
        const res = await axios.get(`http://localhost:8080/api/v1/word/get/${id}`);
        setWord(res.data);
    };

    useEffect(() => {
        loadWord();
    }, []);
    
    const deleteWord = async id => {
        await axios.delete(`http://localhost:8080/api/v1/word/delete/${id}`);
        navigate(`/viewWordList/${word.wordListId}`);
    }

  return (
    <div className="container">
        <div className="card border-secondary mb-3 mt-3" >
        <div className="card-header text-danger fs-3">{word.name}</div>
            <div className="card-body text-success">
            <h5 className="card-title fs-4">{word.definition}</h5>
            <ul className="list-group">
                <p className="card-text text-warning fs-5"> {word.level}</p>
                <p className="card-text text-info fs-5"> {word.status}</p>
                <p className="card-text text-dark fs-5 mb-4 mt-3">Example Sentences:</p>
                {word.exampleSentences.map((sentence, i) => ( 
                    <li className="list-group-item blockquote-footer " key={i}>{sentence}</li>
                ))}
            </ul>
            <button className="btn btn-outline-danger mx-2" onClick={() => deleteWord(word.id)}>
                <RiDeleteBin6Line />
            </button>
            <Link className="btn btn-outline-primary mx-2" to={`/editword/${word.id}`}>
                <RiEdit2Line />
            </Link>
        </div>
        </div>
        <Link className="btn btn-outline-primary mx-2 mt-2" to={`/viewWordList/${word.wordListId}`}>
            Back
        </Link>
    </div>
  )
}
