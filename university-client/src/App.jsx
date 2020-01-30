import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import CssBaseline from '@material-ui/core/CssBaseline';
import RouterView from 'routes';
import AppNavBar from 'components/AppNavBar';

const App = () => (
  <>
    <CssBaseline />
    <Router>
      <AppNavBar />
      <RouterView />
    </Router>
  </>
);

export default App;
