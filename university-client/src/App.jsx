import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import Header from './components/Header';
import Footer from './components/Footer';
import SideNavBar from './components/SideNavBar';
import RouterView from './routes';
import Row from "./components/Row";
import Container from "./components/Container";

export default function App() {
  return (
    <Router>
      <Header />
      <Container>
        <Row>
          <SideNavBar />
          <RouterView />
        </Row>
      </Container>
      <Footer />
    </Router>
  );
}
