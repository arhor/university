import React from 'react';

export default function NavItem({children}) {
  return (
    <li className="nav-item">
      {children}
    </li>
  );
}