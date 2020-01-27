import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import service from 'services/HomeService.js';

export default function HomeView() {
  const [langs, setLangs] = useState(['loading langs...']);
  const [roles, setRoles] = useState(['loading roles...']);

  useEffect(() => {
    service.fetchLangs().then(res => setLangs(res));
    service.fetchRoles().then(res => setRoles(res));
  }, []);

  return (
    <>
      <h2>Welcome to the University App</h2>

      {langs}
      <br />
      {roles}
      <br />
      <Link to="/signin">Sign in</Link>
      <Link to="/signup">Sign up</Link>
    </>
  );
}