import React from 'react';
import Container from './Container';
import Row from './Row';

export default function MainContent({children}) {
  return (
    <Container>
      <Row>
        {children}
      </Row>
    </Container>
  );
}