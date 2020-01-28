import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import CssBaseline from '@material-ui/core/CssBaseline';
import RouterView from 'routes';
import AppNavBar from 'components/AppNavBar';

export default function App() {
    console.debug(styles);
  return (
    <>
      <CssBaseline />
      <Router>
        <AppNavBar />
        <RouterView />
      </Router>
    </>
  );
}
