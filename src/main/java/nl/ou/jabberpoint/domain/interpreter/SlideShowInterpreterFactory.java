package nl.ou.jabberpoint.domain.interpreter;

import nl.ou.jabberpoint.domain.SlideShow;

interface SlideShowInterpreterFactory {
    SlideShowInterpreter createInterpreter(SlideShow slideShow);
}
