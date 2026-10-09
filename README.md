# AirMouse

### A wearable, gesture-controlled mouse built for the Galaxy Watch 4

AirMouse is an experimental Wear OS project that turns a Galaxy Watch into a motion-controlled cursor using wrist movement and gesture recognition.

The main idea was to make the cursor feel as natural as possible. I wanted it to feel less like controlling a device attached to my wrist and more like moving a cursor with my fingertip.

---

## Why I built it

I have always worn my Galaxy Watch, but most of its use came down to checking the time, notifications, fitness tracking and controlling basic things.

I wanted to see if I could make better use of the sensors already available on the watch.

I was also a big fan of an application called WowMouse, which allowed a wearable device to work as a gesture-controlled input device. After it was no longer available, I decided to try building my own version and eventually open-source it.

The idea was not limited to controlling a computer. I wanted something that could eventually be used for things like:

- Controlling music while cooking
- Navigating a television without a physical remote
- Controlling a computer without touching a mouse
- Swiping through or dismissing notifications
- Combining cursor control with voice input from the watch
- Controlling volume through gestures
- Interacting with devices without needing physical contact

The general idea was inspired by the kind of interaction seen in futuristic interfaces, particularly the way Tony Stark interacts with his systems.

---

## How it works

AirMouse uses the sensors on the Galaxy Watch to interpret wrist movement and translate it into cursor movement.

I initially designed the application around wearing the watch on my left wrist, with the watch buttons on the right side.

One thing I specifically wanted to avoid was making the movement feel like it was based on the physical orientation of the watch itself. Instead, I wanted it to feel based on the orientation of my wrist.

For example, with the way I wear the watch:

- Moving my wrist towards the lower end of the watch moves the cursor right.
- Moving my wrist towards the upper end of the watch moves the cursor left.
- Tilting the right side of the watch upwards moves the cursor up.
- Tilting the right side downwards moves the cursor down.

This is not a particularly complicated feature, but I considered it important because I wanted the interaction to feel natural. I did not want to consciously think about which direction the watch was pointing every time I moved my hand.

Cursor speed also depended on the speed of the wrist movement. Slow movements allowed for more controlled positioning, while faster movements allowed the cursor to travel further.

---

## Sensors

I experimented with several sensors available on the Galaxy Watch 4:

- Accelerometer
- Gyroscope
- Touchscreen
- Vibration sensors
- Pulse-related sensors

The accelerometer and gyroscope were the main sensors used for cursor movement.

I initially worked with raw accelerometer and gyroscope data because I wanted to keep the application lightweight and responsive. The raw readings were then filtered and smoothed extensively because even very small movements could otherwise cause unwanted cursor movement.

---

## Calibration and cursor positioning

One of the first major problems was establishing a reliable starting position.

When the watch connected to the target device, I needed to establish a neutral wrist position corresponding to the cursor's initial position.

Early versions had a problem where the origin would reset whenever the watch screen turned off. This caused the cursor to become misaligned and sometimes resulted in lag or unexpected movement.

I eventually fixed this by changing the way sensor usage and permissions were handled so that the required sensor processing could continue even when the watch display was off.

I also had to deal with what happened when the cursor reached the edge of the screen.

If I continued moving my wrist after the cursor had reached an edge, the physical position of my hand and the cursor position could become increasingly misaligned.

I experimented with using the edges of the screen as a reference point. For example, if my finger was physically offset from where the cursor was, I could move the cursor to the edge of the screen and then continue moving my finger until it reached the same effective boundary. This allowed the physical and cursor positions to be brought back into alignment without restarting the connection.

---

## Sensitivity and movement

The first versions were far too sensitive.

The cursor would react to extremely small movements, which caused flickering and made precise control difficult.

Simply reducing the sensitivity was not a good solution because then the cursor became too slow and required large wrist movements.

I therefore had to tune the sensitivity together with cursor acceleration.

The behaviour I was aiming for was:

- Small involuntary movements should not move the cursor much.
- Intentional movements should be immediately recognised.
- Faster movements should move the cursor further.
- The cursor should remain stable when the wrist is relatively still.

This took a lot of experimentation. I changed thresholds, sensitivity and acceleration multiple times until the movement felt natural.

---

## Reducing latency

Another major problem was the delay between moving my wrist and seeing the cursor move.

Some early versions had too much buffering and processing between the sensor input and cursor movement.

I spent a lot of time fine-tuning the implementation and removing unnecessary buffering. The goal was to reduce the delay without making the cursor unstable.

Eventually, the movement became smooth enough that the cursor felt almost directly connected to my finger.

I could use it continuously for hours without noticeable overheating of the watch.

---

## Gesture recognition

The original idea for AirMouse was much bigger than simply moving a cursor.

I wanted to eventually use finger and hand gestures for different actions, including:

- Single index-finger and thumb taps
- Double taps
- Tap-and-movement gestures
- Upward finger or palm gestures
- Downward finger or palm gestures
- Volume control

For example, I wanted a simple index-thumb tap to act as a click, while other combinations could eventually be used for things such as double-clicking or changing volume.

The gesture-recognition system was partially functional, but I never got it to a point where I was satisfied with its reliability.

---

## The finger-tap problem

This became the biggest unresolved part of the project.

I was trying to detect index-thumb taps using the available sensor data. The system could recognise some taps, but the detection depended too much on the intensity and timing of the tap.

A stronger tap could also introduce a small wrist movement, which then caused the cursor to shift.

I experimented with different sensitivity values and different time intervals between taps, but I could not get the detection reliable enough.

I also looked into using the pulse-related sensors on the watch, but I did not yet understand those sensors well enough to make them useful for reliable gesture detection.

This was the point where I paused the project because I had other academic commitments.

---

## Technology

- Device: Samsung Galaxy Watch 4
- Platform: Wear OS
- Development environment: Android Studio
- Language: Kotlin
- Primary sensors: Accelerometer and gyroscope
- AI development tools: Google Gemini and Claude

---

## AI-assisted development

AirMouse was one of my first projects where I used AI heavily for software development.

At the time, I had no knowledge of Kotlin, so Gemini and Claude generated most of the initial code.

However, the development process was not simply copying code and hoping it worked.

I was responsible for deciding what I wanted the application to do, explaining the behaviour to the AI tools, testing the implementation, identifying problems and repeatedly asking for or making changes.

A large part of the project involved recognising when the generated code was not doing what I wanted and figuring out why.

I also modified the generated code myself while fixing problems and debugging different parts of the application.

This project taught me how useful AI can be when learning a new technology, but it also showed me its limitations. Generated code can be technically valid while still being completely wrong for the behaviour you are trying to achieve.

---

## Testing

Testing was probably the biggest part of the project.

I tested the application more than 100 times, often after individual changes.

A change that fixed one problem could easily introduce another. For example, reducing sensitivity could stop unwanted cursor movement but also make the cursor too slow. Improving responsiveness could make small sensor noise more noticeable.

I therefore had to keep testing the actual application rather than assuming that a change was an improvement just because the code looked better.

The most satisfying part of the project was eventually reaching a point where the cursor movement felt extremely precise and natural.

---

## What I learned

### Fine-tuning matters

Getting the first version working was not the difficult part.

Making it feel good to use was much harder.

A large amount of the project was spent adjusting small things such as sensitivity, filtering, acceleration, thresholds and latency.

### Features affect each other

Adding a new feature can easily break something that was already working.

The larger the project became, the more important it became to understand how different parts of the application interacted.

### Testing is part of development

Many of the improvements came from actually using the application and noticing something that did not feel right.

The best way I found to improve it was to test, identify the problem, change something, and test again.

### AI does not replace understanding

AI made it possible for me to work with Kotlin despite having no previous experience with it.

However, it did not remove the need to understand what I wanted to build or to recognise when the generated implementation was wrong.

I still had to define the behaviour, test the application, identify problems and decide how they should be fixed.

### Building something good takes time

The project was much more time-consuming than I initially expected.

The basic idea was relatively simple, but getting the interaction to feel right required a lot of testing and patience.

---

## Current status

AirMouse is currently **temporarily paused**.

The cursor-control portion reached a state where I was genuinely happy with the movement. It was smooth, responsive and could be used for extended periods.

The gesture-control portion, particularly reliable finger-tap detection, was not finished.

I paused the project because I had exams and was preparing to start university. I plan to return to it once I am more settled into my university schedule.

When I return to it, I may rebuild parts of the project from scratch rather than simply continuing the existing implementation. I now have a much better understanding of the problems I encountered and what I would want to do differently.

---

## Future plans

Some of the things I would like to explore when development resumes:

- Reliable index-thumb tap detection
- Double-tap recognition
- Gesture-based clicking
- Gesture-based volume control
- Better sensor fusion
- Improved filtering
- More robust calibration
- Right-hand support
- Customisable sensitivity
- User-configurable gestures
- Better power optimisation
- Broader Wear OS compatibility
- Voice and gesture interaction
- Open-source release

The long-term goal is to turn AirMouse into something more general than a motion-controlled mouse: a wearable interface for interacting with different devices.

---

## Author

**Syed Mustafa Hussain Rizvi**

Computer Science (Artificial Intelligence) Undergraduate  
Universiti Malaya, Malaysia

Interested in:

Artificial Intelligence, Machine Learning, Human-Computer Interaction, Wearable Technology and Software Development.

---

## License

A license will be added when the project is prepared for public release.