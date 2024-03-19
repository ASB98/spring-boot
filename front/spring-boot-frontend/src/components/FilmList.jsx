import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

function FilmList() {
  const [films, setFilms] = useState([]);
  const [searchQuery, setSearchQuery] = useState(''); //state to track search query
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 20;
  const navigate = useNavigate();

  useEffect(() => {
    const fetchFilms = async () => {
      try {
        const response = await axios.get('http://localhost:8080/home/allFilms');
        setFilms(response.data);
      } catch (error) {
        console.error('Failed to fetch films:', error);
      }
    };
    fetchFilms();
  }, []);

  const deleteFilm = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/home/deleteFilm/${id}`);
      setFilms(films.filter(film => film.filmID !== id));
    } catch (error) {
      console.error('Failed to delete film:', error);
    }
  };

  //apply search filter to films
  const filteredFilms = searchQuery
    ? films.filter(film =>
        film.title.toLowerCase().includes(searchQuery.toLowerCase())
      )
    : films;

  let totalPages = Math.ceil(filteredFilms.length / itemsPerPage);
  const filmsToShow = filteredFilms.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage
  );

  const handlePrevClick = () => setCurrentPage((prevPage) => Math.max(prevPage - 1, 1));
  const handleNextClick = () => setCurrentPage((prevPage) => Math.min(prevPage + 1, totalPages));

  return (
    <div>
      <h2>Film List</h2>
      <div>
        <input
          type="text"
          placeholder="Search films by name..."
          value={searchQuery}
          onChange={(e) => {
            setSearchQuery(e.target.value);
            setCurrentPage(1); //reset to first page on new search
          }}
        /></div>
      <div>
        <p></p>
        <Link to="/addFilm">Add New Film</Link>
      </div>
      {filmsToShow.map((film) => (
        <div key={film.filmID}>
          <Link to={`/film/${film.filmID}`} style={{ textDecoration: 'none', color: 'white' }}>
            <h3>{film.title}</h3>
          </Link>
          <p>{film.description}</p>
          <button onClick={() => deleteFilm(film.filmID)}>Delete</button>
          <button onClick={() => navigate(`/updateFilm/${film.filmID}`)}>Update</button>
        </div>
      ))}
      <p></p>
      <div>
        <button onClick={handlePrevClick} disabled={currentPage === 1}>Previous</button>
        <span> Page {currentPage} of {totalPages} </span>
        <button onClick={handleNextClick} disabled={currentPage === totalPages}>Next</button>
      </div>
    </div>
  );
}

export default FilmList;
