BUILD_DIR=build
SOURCE_FILES:=$(shell find . -name "*.java")
OUTPUT_FILES:=$(patsubst %.java,$(BUILD_DIR)/%.class,$(SOURCE_FILES))

.PHONY: all build

all: $(SOURCE_FILES)

$(OUTPUT_FILES): $(SOURCE_FILES)
	javac $(SOURCE_FILES) -d $(BUILD_DIR)

run: $(OUTPUT_FILES)
	java -cp $(BUILD_DIR) Main