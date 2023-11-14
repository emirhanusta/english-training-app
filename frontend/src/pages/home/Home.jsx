import React from 'react'
import { Link } from 'react-router-dom';
import Words from '../word/Words';

export default function Home() {
  return (
    <div class="container">
        <Words/>
        <div class="row">
          <div class="col-sm-6 mb-3 mb-sm-0">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Diarys</h5>
                <p class="card-text">See your diarys.</p>
                <Link to="/viewdiarys" className="btn btn-info">Go Diary</Link>
              </div>
            </div>
          </div>
          <div class="col-sm-6">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Word Lists</h5>
                <p class="card-text">Se your word lists.</p>
                <Link to="/viewwordlists" className='btn btn btn-info'>Go Word Lists</Link>
              </div>
            </div>
          </div>
        </div>   
   </div>
  )
}
