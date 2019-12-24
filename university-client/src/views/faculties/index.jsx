import React, { useState, useEffect } from 'react';
import service from './service/FacultyService.js';

export default function FacultiesView() {
  const [faculties, setFaculties] = useState([]);

  useEffect(() => {
    service.fetchFaculties().then(data => setFaculties(data));
  }, []);

  return (
    <>
      <h2>Faculties View</h2>
    </>
  );
}