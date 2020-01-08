import React, { useState, useEffect } from 'react';
import service from './service/FacultyService.js';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';

export default function FacultiesView() {
  const [faculties, setFaculties] = useState([]);

  useEffect(() => {
    service.fetchFaculties().then(data => setFaculties(data));
  }, []);

  return (
    <>
      <Typography variant="h4">Faculties</Typography>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Id</TableCell>
            <TableCell align="right">Title</TableCell>
            <TableCell align="right">Seats Budget</TableCell>
            <TableCell align="right">Seats Paid</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {faculties.map(row => (
            <TableRow key={row.id}>
              <TableCell component="th" scope="row">{row.id}</TableCell>
              <TableCell align="right">{row.defaultTitle}</TableCell>
              <TableCell align="right">{row.seatsBudget}</TableCell>
              <TableCell align="right">{row.seatsPaid}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </>
  );
}