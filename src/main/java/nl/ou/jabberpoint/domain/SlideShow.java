package nl.ou.jabberpoint.domain;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import nl.ou.jabberpoint.domain.collections.PresentationIterator;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class SlideShow implements PresentableElement, Iterable<Slide> {
    private final PropertyChangeSupport slideShowChangedSupportProperty = new PropertyChangeSupport(this);
    private String title;
    private MetaData metaData;
    private PresentableElement backdrop;
    private PresentableElement header;
    private PresentableElement footer;
    private PresentationIterator slideShowIterator;
    private int slideNr;
    private Slide currentSlide;
    private LinkedList<Slide> slideList;

    public SlideShow(LinkedList<Slide> slides) {
        slideList = slides;
        slideShowIterator = new SlideShowIterator((ListIterator<Slide>) slides.iterator());
    }

    @Override
    public void draw(GraphicsContext graphics, PresentableElementBounds slideShowBounds, double scale, Point drawLocation, MouseEvent mouseEvent) {
        backdrop.draw(graphics, slideShowBounds, scale, drawLocation, mouseEvent);
        header.draw(graphics, slideShowBounds, scale, drawLocation, mouseEvent);
        footer.draw(graphics, slideShowBounds, scale, drawLocation, mouseEvent);

        if (Optional.ofNullable(currentSlide).isPresent()) {
            currentSlide.draw(graphics, slideShowBounds, scale, drawLocation, mouseEvent);
        }
    }

    public Slide getCurrentSlide() {
        return currentSlide;
    }

    private void setCurrentSlide(Slide currentSlide) {
        slideShowChangedSupportProperty.firePropertyChange("currentSlide", this.currentSlide, currentSlide);
        this.currentSlide = currentSlide;
    }

    @Override
    public double calculateWidth(GraphicsContext graphics, double scale) {
        double width = 0;
        for (Slide slide : slideList) {
            width += slide.calculateWidth(graphics, scale);
        }
        return width;
    }

    @Override
    public double calculateHeight(GraphicsContext graphics, double scale) {
        double height = 0;
        for (Slide slide : slideList) {
            height += slide.calculateHeight(graphics, scale);
        }
        return height;
    }

    public PresentableElement goToSlide(int slideNr) {
        PresentableElement previousSlideContent = null;

        if (Optional.ofNullable(currentSlide).isPresent()) {
            previousSlideContent = currentSlide.getCurrentSlideContent();
        }

        if (slideNr > slideList.size() || slideNr < 1) throw new IllegalStateException();

        slideShowIterator = new SlideShowIterator((ListIterator<Slide>) slideList.iterator());

        while (this.slideNr < slideNr) {
            this.nextSlide();
        }

        slideShowChangedSupportProperty.firePropertyChange("slide", previousSlideContent, currentSlide);
        return currentSlide;
    }

    public PresentableElement nextStep() {
        PresentableElement previousSlideContent = null;
        PresentableElement slideItem;

        if (Optional.ofNullable(currentSlide).isPresent()) {
            previousSlideContent = currentSlide.getCurrentSlideContent();
        }

        slideItem = slideShowIterator.next();

        if (slideItem == previousSlideContent && slideShowIterator.hasNext()) {
            slideItem = nextStep();
        }

        slideShowChangedSupportProperty.firePropertyChange("slideStep", previousSlideContent, slideItem);
        return slideItem;
    }

    public PresentableElement previousStep() {
        PresentableElement previousSlideContent = null;
        PresentableElement slideItem;

        if (Optional.ofNullable(currentSlide).isPresent()) {
            previousSlideContent = currentSlide.getCurrentSlideContent();
        }

        slideItem = slideShowIterator.previous();

        if (slideItem == previousSlideContent) {
            slideItem = previousStep();
        }

        slideShowChangedSupportProperty.firePropertyChange("slideStep", previousSlideContent, slideItem);

        return slideItem;

    }

    public PresentableElement nextSlide() {
        PresentableElement previous = currentSlide;
        slideShowIterator.nextSlide();
        slideShowChangedSupportProperty.firePropertyChange("slideChanged", previous, currentSlide);
        return currentSlide;
    }

    public PresentableElement previousSlide() {
        PresentableElement previous = currentSlide;
        slideShowIterator.previousSlide();
        slideShowChangedSupportProperty.firePropertyChange("slideChanged", previous, currentSlide);
        return currentSlide;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Iterator<Slide> iterator() {
        return slideList.iterator();
    }

    public int getSlideNr() {
        return slideNr;
    }

    public void addSlideShowChangedListener(PropertyChangeListener pcl) {
        slideShowChangedSupportProperty.addPropertyChangeListener(pcl);
    }

    public List<Slide> getSlides() {
        return this.slideList;
    }

    public void setSlides(LinkedList<Slide> newSlideDeck) {
        this.slideList = newSlideDeck;
    }

    public PresentableElement getFooter() {
        return footer;
    }

    public void setFooter(PresentableElement footerVisualisation) {
        this.footer = footerVisualisation;
    }

    public void setBackdrop(PresentableElement backdropVisualisation) {
        this.backdrop = backdropVisualisation;
    }

    public void setHeader(PresentableElement headerVisualisation) {
        header = headerVisualisation;
    }

    private class SlideShowIterator implements PresentationIterator {
        private final ListIterator<Slide> slideDeckIterator;
        private ListIterator<SlideItem> slideItemIterator;

        SlideShowIterator(ListIterator<Slide> slideDeckIterator) {
            this.slideDeckIterator = slideDeckIterator;
            slideNr = 0;
        }

        @Override
        public boolean hasNext() {
            return (Optional.ofNullable(slideItemIterator).isPresent() && slideItemIterator.hasNext()) || slideDeckIterator.hasNext();
        }

        @Override
        public PresentableElement next() {

            if (!hasNext()) throw new NoSuchElementException();

            PresentableElement slideItem = currentSlide;
            if ((Optional.ofNullable(slideItemIterator).isEmpty() || !slideItemIterator.hasNext()) && slideDeckIterator.hasNext()) {
                slideItem = nextSlide();

            } else if (slideItemIterator.hasNext()) {
                try {
                    slideItem = slideItemIterator.next();
                } catch (NoSuchElementException e) {
                    nextSlide();
                    slideItem = getCurrentSlide().getCurrentSlideContent();
                }
            }

            return slideItem;
        }

        @Override
        public boolean hasPrevious() {

            return slideDeckIterator.hasPrevious() || slideItemIterator.hasPrevious();
        }

        @Override
        public PresentableElement previous() {
            if (!hasPrevious()) throw new NoSuchElementException();

            PresentableElement slideItem = currentSlide;
            if (!slideItemIterator.hasPrevious() && hasPrevious()) {
                slideItem = previousSlide();
            } else if (slideItemIterator.hasPrevious()) {
                try {
                    slideItem = slideItemIterator.previous();
                } catch (NoSuchElementException e) {
                    previous();
                }
            }

            return slideItem;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(PresentableElement slideItem) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(PresentableElement slideItem) {
            throw new UnsupportedOperationException();
        }

        @Override
        public PresentableElement nextSlide() {
            // keep track of the previous slide item
            // we need to check whether or not te previous item item was the exact same as
            // the current one. This is because the cursor of the iterator never points to a specific element.
            // it is always in between two elements
            Slide slideItem;
            if (!slideDeckIterator.hasNext()) throw new NoSuchElementException();

            slideItem = slideDeckIterator.next();

            if (Optional.ofNullable(slideItem).isPresent() && slideItem.equals(getCurrentSlide())) {
                this.nextSlide();
            } else if (slideNr < slideList.size()) {
                setCurrentSlide(slideItem);
                slideNr++;

                slideItemIterator = (ListIterator<SlideItem>) getCurrentSlide().iterator();
                slideItemIterator.next();
            }

            return currentSlide;
        }

        @Override
        public PresentableElement previousSlide() {
            // keep track of the previous slide item
            // we need to check whether or not te previous item item was the exact same as
            // the current one. This is because the cursor of the iterator never points to a specific element.
            // it is always in between two elements
            Slide slideItem;
            if (!slideDeckIterator.hasPrevious()) throw new NoSuchElementException();

            slideItem = slideDeckIterator.previous();

            if (Optional.ofNullable(slideItem).isPresent() && slideItem.equals(getCurrentSlide())) {
                slideItem = (Slide) previousSlide();
            } else if (slideNr > 1) {
                setCurrentSlide(slideItem);
                slideNr--;

                slideItemIterator = (ListIterator<SlideItem>) getCurrentSlide().iterator(currentSlide.getNumberOfSteps());
            }

            return slideItem;
        }
    }

}
