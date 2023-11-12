import React from 'react'
import { Link } from "react-router-dom";

export default function navbar() {
  return (
    <div>
      <nav className="navbar navbar-expand-lg navbar-dark bg-secondary">
        <div className="container-fluid">
          <Link className="navbar-brand" to="/">
            English Training
          </Link>

        </div>
      </nav>
    </div>
  )
}
