import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { Link } from 'react-router-dom';

function FilmDetails() {
    const { id } = useParams();
    const [film, setFilm] = useState(null);
    const [languages, setLanguages] = useState([]);

    useEffect(() => {
        const fetchFilmAndLanguages = async () => {
            try {
                const [filmResponse, languagesResponse] = await Promise.all([
                    axios.get(`http://localhost:8080/home/film/${id}`),
                    axios.get('http://localhost:8080/home/allLanguages'),
                ]);

                setFilm(filmResponse.data);
                setLanguages(languagesResponse.data);
            } catch (error) {
                console.error('Failed to fetch film or languages:', error);
            }
        };

        fetchFilmAndLanguages();
    }, [id]);

    const getLanguageName = (languageID) => {
        const language = languages.find(lang => lang.languageId === languageID);
        return language ? language.name : 'Unknown';
    };

    if (!film) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h2>{film.title}</h2>
            <p>{film.description}</p>
            <h3>Released: {film.releaseYear}</h3>
            <h3>Language: {getLanguageName(film.languageID)}</h3>
            <h3>Actors:</h3>
            <ul style={{ listStyleType: "none", padding: 0 }}>
                {film.actors.map(actor => (
                    <Link to={`/actorMovies/${actor.actorID}`} style={{ textDecoration: 'none', color: 'white' }}>
                        <li key={actor.actorID}>{actor.firstName} {actor.lastName}</li>
                    </Link>
                ))}
            </ul>
        </div>
    );
}

export default FilmDetails;
