import React from 'react'
import { Link } from 'react-router-dom';
import WordList from '../wordList/WordList';

export default function Home() {
  return (
    <div class="container">
        <WordList/>
        <div class="row">
          <div class="col-sm-6 mb-3 mb-sm-0">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Diarys</h5>
                <p class="card-text">See your diarys.</p>
                <Link to="/viewdiarys" className="btn btn-secondary">Go Diary</Link>
              </div>
            </div>
          </div>
          <div class="col-sm-6">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Words</h5>
                <p class="card-text">Se your words.</p>
                <Link to="/viewwords" className='btn btn-secondary'>Go Words</Link>
              </div>
            </div>
          </div>
        </div>   
   </div>
  )
}
