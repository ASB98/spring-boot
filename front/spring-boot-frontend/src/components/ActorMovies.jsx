import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { Link } from 'react-router-dom';

function ActorMovies() {
  const { actorID } = useParams(); // Using useParams hook to get actorID from the URL
  const [actorMovies, setActorMovies] = useState([]);
  const [actorName, setActorName] = useState('');

  useEffect(() => {
    const fetchMoviesByActor = async () => {
      try {
        const moviesResponse = await axios.get(`http://localhost:8080/home/filmsByActor/${actorID}`);
        setActorMovies(moviesResponse.data);
        const actorResponse = await axios.get(`http://localhost:8080/home/actor/${actorID}`);
        setActorName(`${actorResponse.data.firstName} ${actorResponse.data.lastName}`);
      } catch (error) {
        console.error('Failed to fetch movies or actor:', error);
      }
    };

    fetchMoviesByActor();
  }, [actorID]);

  return (
    <div>
      <h2>Movies featuring {actorName}</h2>
      {actorMovies.length > 0 ? (
        <ul style={{ listStyleType: "none", padding: 0 }}>
          {actorMovies.map(movie => (
            <Link to={`/film/${movie.filmID}`} style={{ textDecoration: 'none', color: 'white' }}>
            <li key={movie.filmID}>{movie.title}</li>
            </Link>
          ))}
        </ul>
      ) : (
        <p>No movies found for this actor.</p>
      )}
    </div>
  );
}

export default ActorMovies;
