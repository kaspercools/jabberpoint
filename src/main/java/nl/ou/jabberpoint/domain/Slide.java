package nl.ou.jabberpoint.domain;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.ArcType;
import nl.ou.jabberpoint.domain.builder.ItemBuilder;
import nl.ou.jabberpoint.domain.factories.DefaultSlideFactory;

import java.net.URISyntaxException;
import java.util.*;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class Slide implements PresentableElement, Iterable<Object> {
    private final LinkedList<SlideItem> slideStepList;
    private final ListIterator<SlideItem> slideStepIterator;
    private final SlideType type;
    private String title;
    private int slideStepNr;
    private SlideItem currentSlideContent;
    private Integer id;

    private Slide(SlideBuilder slideBuilder) {
        this.type = slideBuilder.type;
        this.title = slideBuilder.title;
        this.id = slideBuilder.id;
        this.slideStepList = slideBuilder.slideStepList;
        this.slideStepIterator = new SlideIterator((ListIterator<SlideItem>) this.slideStepList.iterator());
    }

    SlideItem getCurrentSlideContent() {
        return currentSlideContent;
    }

    @Override
    public double calculateHeight(GraphicsContext graphics, double scale) {
        //TODO calculate total height
        return 0;
    }

    @Override
    public double calculateWidth(GraphicsContext graphics, double scale) {
        //TODO calculate total width
        return 0;
    }

    @Override
    public void draw(GraphicsContext graphics, PresentableElementBounds slideShowBounds, double scale, Point drawLocation, MouseEvent mouseEvent) {
        if (Optional.ofNullable(currentSlideContent).isPresent()) {
            currentSlideContent.draw(graphics, slideShowBounds, scale, drawLocation, mouseEvent);
        }
    }

    public int getNumberOfSteps() {
        return slideStepList.size();
    }

    public SlideType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSlideStepNr() {
        return slideStepNr;
    }

    public PresentableElement nextStep() {
        currentSlideContent = slideStepIterator.next();
        return currentSlideContent;
    }

    public PresentableElement previousStep() {
        currentSlideContent = slideStepIterator.previous();
        return currentSlideContent;
    }

    @Override
    public String toString() {
        return "Slide{" +
                "type=" + type +
                "# steps=" + slideStepList.size() +
                '}';
    }

    @Override
    public Iterator iterator() {
        return new SlideIterator(slideStepList.listIterator());
    }

    Iterator iterator(int startPos) {
        return new SlideIterator(slideStepList.listIterator(), startPos);

    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public static final class SlideBuilder implements ItemBuilder {
        private final DefaultSlideFactory slideFactory;
        private String title;
        private LinkedList<SlideItem> slideStepList;
        private SlideType type;
        private SlideItem currentSlideItem;
        private Integer id;

        public SlideBuilder(DefaultSlideFactory slideFactory) {
            slideStepList = new LinkedList<>();
            this.slideFactory = slideFactory;
        }

        public ItemBuilder addArc(double x, double y, double width, double height, double startAngle, double extent, ArcType closure, Style style) {
            currentSlideItem = slideFactory.createArc(new Point(x, y), width, height, startAngle, extent, closure, style, currentSlideItem);
            return this;
        }

        public ItemBuilder addCircle(double x, double y, double radius, Style color) {
            return addOval(x, y, radius, radius, color);
        }

        public ItemBuilder addImage(String imagePath) throws URISyntaxException {
            currentSlideItem = slideFactory.createImage(imagePath, currentSlideItem);
            return this;
        }

        public ItemBuilder addLine(double fromX, double fromY, double toX, double toY, Style style) {

            currentSlideItem = slideFactory.createLine(new Point(fromX, fromY), new Point(toX, toY), style, currentSlideItem);
            return this;
        }

        public ItemBuilder addOval(double x, double y, double width, double height, Style style) {
            currentSlideItem = slideFactory.createOval(new Point(x, y), width, height, style, currentSlideItem);
            return this;
        }

        public ItemBuilder addRectangle(double x, double y, double width, double height, Style style) {
            currentSlideItem = slideFactory.createRectangle(new Point(x, y), width, height, style, currentSlideItem);
            return this;
        }

        public ItemBuilder addSquare(double x, double y, double width, Style style) {
            return addRectangle(x, y, width, width, style);
        }

        public ItemBuilder addText(String text) {
            currentSlideItem = slideFactory.createTextItem(text, currentSlideItem);
            return this;
        }

        public ItemBuilder id(int id) {
            this.id = id;
            return this;
        }

        public Slide build() {
            addSlideStep(currentSlideItem);
            Slide resultingSlide = new Slide(this);
            reset();

            return resultingSlide;
        }

        public boolean hasTitle() {
            return Optional.ofNullable(title).isPresent();
        }

        public void level(int level) {
            currentSlideItem.setLevel(level);
        }

        public ItemBuilder position(Point location) {
            currentSlideItem.setPosition(location);
            return this;
        }

        public void reset() {
            slideStepList = new LinkedList<>();
            type = null;
            currentSlideItem = null;
            title = null;
            id = null;
        }

        public ItemBuilder style(Style style) {
            currentSlideItem.setStyle(style);
            return this;
        }

        public ItemBuilder title(String slideTitle) {
            title = slideTitle;
            return this;
        }

        public ItemBuilder transition() {
            addSlideStep(currentSlideItem);
            return this;
        }

        public ItemBuilder type(SlideType newType) {
            type = newType;
            return this;
        }

        private void addSlideStep(SlideItem currentSlideItem) {
            if (Optional.ofNullable(currentSlideItem).isPresent() && !slideStepList.contains(currentSlideItem)) {
                slideStepList.add(currentSlideItem);
            }
        }
    }

    /**
     * acts as a proxy for the underlying collection iterator
     */
    private class SlideIterator implements ListIterator<SlideItem> {

        private final ListIterator<SlideItem> slideStepIterator;

        SlideIterator(ListIterator<SlideItem> iterator) {
            slideStepNr = 0;
            currentSlideContent = null;
            slideStepIterator = iterator;
        }

        SlideIterator(ListIterator<SlideItem> iterator, int startPos) {
            this(iterator);
            startPos -= 1;

            navigateToSlideStep(startPos);
        }

        private void navigateToSlideStep(int slideStepIndex) {
            while (slideStepIndex >= slideStepNr) {
                next();
            }
        }

        @Override
        public boolean hasNext() {
            return slideStepIterator.hasNext();
        }

        @Override
        public SlideItem next() {
            // keep track of the previous slide item
            // we need to check whether or not te previous item item was the exact same as
            // the current one. This is because the cursor of the iterator never points to a specific element.
            // it is always in between two elements
            PresentableElement slideItem = getCurrentSlideContent();
            if (!hasNext()) throw new NoSuchElementException();

            currentSlideContent = slideStepIterator.next();

            if (Optional.ofNullable(slideItem).isPresent() && slideItem.equals(currentSlideContent)) {
                currentSlideContent = next();
            } else if (slideStepNr < slideStepList.size()) {
                slideStepNr++;
            }

            return currentSlideContent;
        }

        @Override
        public boolean hasPrevious() {
            return slideStepIterator.hasPrevious();
        }

        @Override
        public SlideItem previous() {
            // keep track of the previous slide item
            // we need to check whether or not te previous item item was the exact same as
            // the current one. This is because the cursor of the iterator never points to a specific element.
            // it is always in between two elements
            if (!slideStepIterator.hasPrevious()) throw new NoSuchElementException();

            PresentableElement slideItem = getCurrentSlideContent();
            currentSlideContent = slideStepIterator.previous();

            if (Optional.ofNullable(slideItem).isPresent() && slideItem.equals(currentSlideContent)) {
                currentSlideContent = previous();
            }

            if (slideStepNr > 1) {
                slideStepNr--;
            }

            return currentSlideContent;
        }

        @Override
        public int nextIndex() {
            return slideStepIterator.nextIndex();
        }

        @Override
        public int previousIndex() {
            return slideStepIterator.previousIndex();
        }

        @Override
        public void remove() {
            slideStepIterator.remove();
        }

        @Override
        public void set(SlideItem slideItem) {
            slideStepIterator.set(slideItem);
        }

        @Override
        public void add(SlideItem slideItem) {
            slideStepIterator.add(slideItem);
        }
    }

}
