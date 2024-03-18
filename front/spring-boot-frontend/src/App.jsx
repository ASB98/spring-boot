import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import FilmList from './components/FilmList';
import ActorList from './components/ActorList';
import UpdateActor from './components/UpdateActor';
import UpdateFilm from './components/UpdateFilm';
import AddFilm from './components/AddFilm';
import AddActor from './components/AddActor';
import FilmDetails from './components/FilmDetails';
import ActorMovies from './components/ActorMovies';
import Navbar from './components/Navbar';
import './App.css'

function App() {
  return (
    <Router>
      <div>
      <Navbar />
        <Routes>
          <Route path="/home" element={<FilmList />} />
          <Route path="/home/actors" element={<ActorList />} />
          <Route path="/updateActor/:id" element={<UpdateActor />} />
          <Route path="/addActor" element={<AddActor />} />
          <Route path="/updateFilm/:id" element={<UpdateFilm />} />
          <Route path="/addFilm" element={<AddFilm />} />
          <Route path="/film/:id" element={<FilmDetails />} />
          <Route path="/actorMovies/:actorID" element={<ActorMovies />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;

