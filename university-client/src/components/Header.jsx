import React from 'react';
import { Link } from 'react-router-dom';

export default function Header() {
  return (
    <header>
      <nav className="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
        <Link to="/" className="navbar-brand col-sm-3 col-md-2 mr-0">University</Link>
        <input className="form-control form-control-dark w-100" type="text" placeholder="Search" aria-label="Search" />
        <ul className="navbar-nav px-3">
          <li className="nav-item text-nowrap">
            <a className="nav-link" href="#">Sign out</a>
          </li>
        </ul>
      </nav>
    </header>
  );
}