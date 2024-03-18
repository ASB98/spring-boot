import React from 'react';
import { Link } from 'react-router-dom';

function Navbar() {
  return (
    <nav style={{ padding: '20px', backgroundColor: '#f0f0f0' }}>
      <Link to="/home" style={{ padding: '10px' }}>Films</Link>
      <Link to="/home/actors" style={{ padding: '10px' }}>Actors</Link>
    </nav>
  );
}

export default Navbar;
