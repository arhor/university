import React from 'react';

export default function Nav({children}) {
  return (
    <ul className="nav flex-column">
      {children}
    </ul>
  );
}