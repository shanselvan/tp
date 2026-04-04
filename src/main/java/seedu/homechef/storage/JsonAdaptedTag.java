package seedu.homechef.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.homechef.commons.exceptions.IllegalValueException;
import seedu.homechef.model.order.DietTag;

/**
 * Jackson-friendly version of {@link DietTag}.
 */
class JsonAdaptedTag {

    private final String tagName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedTag(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Converts a given {@code DietTag} into this class for Jackson use.
     */
    public JsonAdaptedTag(DietTag source) {
        tagName = source.tagName;
    }

    @JsonValue
    public String getTagName() {
        return tagName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code DietTag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public DietTag toModelType() throws IllegalValueException {
        if (!DietTag.isValidTagName(tagName)) {
            throw new IllegalValueException(DietTag.MESSAGE_CONSTRAINTS);
        }
        return new DietTag(tagName);
    }

}
