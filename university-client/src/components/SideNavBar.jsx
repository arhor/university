import React from 'react';
import Nav from './Nav';
import NavItem from './NavItem';
import NavLink from './NavLink';

export default function SideNavBar() {
  return (
    <nav className="col-md-2 d-none d-md-block bg-light sidebar">
      <div className="sidebar-sticky">
        <Nav>
          <NavItem>
            <NavLink to="/">
              Dashboard <span className="sr-only">(current)</span>
            </NavLink>
          </NavItem>

          <NavItem>
            <NavLink to="/">
              Orders
            </NavLink>
          </NavItem>

          <NavItem>
            <NavLink to="/">
               Products
            </NavLink>
          </NavItem>

          <NavItem>
            <NavLink to="/">
              Customers
            </NavLink>
          </NavItem>

          <NavItem>
            <NavLink to="/">
              Reports
            </NavLink>
          </NavItem>

          <NavItem>
            <NavLink to="/">
              Integrations
            </NavLink>
          </NavItem>
        </Nav>

        <h6 className="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
          <span>Saved reports</span>
          <a className="d-flex align-items-center text-muted" href="#" aria-label="Add a new report">
          </a>
        </h6>

        <ul className="nav flex-column mb-2">
          <li className="nav-item">
            <a className="nav-link" href="#">
              Current month
            </a>
          </li>

          <li className="nav-item">
            <a className="nav-link" href="#">
              Last quarter
            </a>
          </li>

          <li className="nav-item">
            <a className="nav-link" href="#">
              Social engagement
            </a>
          </li>

          <li className="nav-item">
            <a className="nav-link" href="#">
              Year-end sale
            </a>
          </li>
        </ul>
      </div>
    </nav>
  );
}