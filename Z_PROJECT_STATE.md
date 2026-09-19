# Z PROJECT STATE — SINGLE SOURCE OF TRUTH

## PROJECT
Name: Z
Repository: Z-AI
Branch: main
Package: com.example.z

## STATUS LEGEND
- [x] COMPLETED = implemented + integrated + tested + verified
- [>] IN PROGRESS = currently being implemented
- [ ] NOT STARTED = planned, not implemented
- [~] LEGACY = generated/old code, not part of active Z architecture
- [!] REVIEW = requires dependency/reference check
- [LOCKED] = cannot start until required earlier steps are complete

---

# DEVELOPMENT ROADMAP

## 1. Foundation & Architecture
Status: [>] IN PROGRESS

Verified implementation:
- [x] Core package created.
- [x] ZCore lifecycle contract created.
- [x] ZCoreImpl created.
- [x] Core lifecycle unit tests created and passing.
- [x] ZApplication created and starts ZCore.
- [x] ZApplication registered in AndroidManifest.
- [x] MainActivity obtains the shared ZCore instance.
- [x] MainActivity passes ZCore to MainNavigation.
- [x] MainNavigation passes ZCore to MainScreen.
- [x] MainScreen displays ZCore state.
- [x] Malformed generated AndroidManifest repaired.
- [x] Minimal Foundation MainScreen created.
- [x] Legacy DataRepository/MainScreenViewModel/NavigationKeys obsolete wiring was removed after reference checking.
- [x] Final reference check for DataRepository/MainScreenViewModel/NavigationKeys/MainKey returned no results.
- [x] compileDebugKotlin successful.
- [x] testDebugUnitTest successful.
- [x] assembleDebug successful.

Still required:
- [ ] Physical Android/emulator runtime launch verification.
- [ ] Final Foundation integration test/acceptance verification that includes the current shared Core -> Activity -> Navigation -> UI path.
- [ ] Final Foundation status change to COMPLETED only after runtime/integration acceptance succeeds.

Acceptance:
Foundation is COMPLETE only after implementation + integration + tests + required runtime verification.

---

## 2. AI Brain / Model Layer
Status: [>] IN PROGRESS

Verified implementation/build/test coverage:
- [x] AIProvider abstraction.
- [x] AIRequest / AIResponse / AITaskType.
- [x] AIProviderCapabilities.
- [x] AIProviderRegistry.
- [x] AIProviderRouter.
- [x] AIBrain / AIBrainImpl.
- [x] ZCore integration with AI Brain.
- [x] ModelRuntime abstraction.
- [x] UnavailableModelRuntime.
- [x] ModelRuntimeInfo.
- [x] LocalAIProvider wired to ModelRuntime.
- [x] Native llama.cpp runtime adapter and Z JNI bridge.
- [x] Native llama.cpp build verification.
- [x] GGUF ModelManager with app-private model storage.
- [x] Android document picker for GGUF import.
- [x] Online OpenAI-compatible provider.
- [x] AIProviderConfig.
- [x] Android Keystore-backed SecureApiKeyStore.
- [x] Online provider request/error handling corrected.
- [x] llama.cpp JNI benchModel naming verified/corrected.
- [x] Kotlin compilation, unit-test and debug APK assembly verification.

Still required:
- [ ] Real Android-device/emulator GGUF load + inference verification.
- [ ] Real Android-device/emulator GGUF streaming verification.
- [ ] Real authorized online API request/response verification.
- [ ] Runtime Android Keystore save/retrieve/clear verification.
- [ ] Complete resource-aware provider selection beyond the current router.
- [ ] Final Step 2 acceptance verification.

Important:
- No fake AI response is being used as a substitute for real runtime verification.
- Do not recreate the existing provider/runtime architecture merely because device verification is pending.

---

## 3. Chat System
Status: [>] IN PROGRESS

Verified implementation:
- [x] ChatMessage user/assistant message model.
- [x] ChatViewModel connected to ZCore/AIBrain.
- [x] Send/request/response lifecycle.
- [x] Stop/cancel generation path.
- [x] Error handling and retry.
- [x] User-message edit/delete actions.
- [x] Empty-input protection.
- [x] Automatic scroll to latest message.
- [x] Keyboard-safe IME padding.
- [x] Responsive Send/Stop UI.
- [x] ChatScreen wired through MainScreen/MainNavigation.
- [x] AIStreamingBrain abstraction.
- [x] AIStreamingProvider abstraction.
- [x] ModelStreamingRuntime abstraction.
- [x] LocalAIProvider streaming path with non-streaming fallback.
- [x] AIBrainImpl streaming routing/provider fallback.
- [x] LlamaCppModelRuntime connected to native generateNextToken() streaming.
- [x] ChatViewModel consumes streaming chunks.
- [x] ChatScreen Send action uses streaming.
- [x] Chat streaming unit test passes.
- [x] ChatViewModel tests pass.
- [x] Kotlin compilation passes after streaming integration.

Still pending:
- [ ] Persistent conversation integration with Step 4 Memory.
- [ ] Real Android-device GGUF streaming/inference verification.
- [ ] Real cancellation behavior verification on Android hardware.
- [ ] Final Step 3 acceptance verification.

Status rule:
Step 3 remains IN PROGRESS until implementation, integration, tests and required runtime verification are complete.

---

## 4. Memory System
Status: [>] IN PROGRESS

Verified implementation:
- [x] kotlinx.serialization JSON dependency added and verified.
- [x] MemoryEntry structured persistent-memory model created.
- [x] MemoryRepository abstraction created.
- [x] JsonMemoryRepository created using app-private `context.filesDir/memory.json`.
- [x] JSON repository supports save/update, get, delete, getAll, search and clear.
- [x] ZCore exposes `MemoryRepository` through the core contract.
- [x] ZCoreImpl uses persistent JsonMemoryRepository on Android.
- [x] InMemoryMemoryRepository fallback added for JVM/non-Android tests where Application context is unavailable.
- [x] Existing ZCore tests remained compatible after Memory integration.
- [x] Pure-JVM JsonMemoryRepository tests created without requiring Android instrumentation.
- [x] Memory tests cover save/get, same-ID update, delete, search and clear.
- [x] Full `app:testDebugUnitTest` verification passed with 29 tests.
- [x] Step 4 implementation commit created: `4cb5f46 Implement Step 4 persistent memory foundation`.
- [x] Step 4 commit pushed to `main`.
- [x] `git pull --ff-only origin main` returned `Already up to date`.
- [x] HEAD verified as `4cb5f46`.

Still pending:
- [x] Persistent conversation/message integration from Chat into Memory.
- [ ] Stronger memory semantics/policy layer for what should and should not be persisted.
- [ ] Explicit privacy/security boundaries for memory access and sensitive data.
- [ ] More complete retrieval behavior for future context use.
- [ ] Memory lifecycle/retention behavior beyond the current repository CRUD layer.
- [ ] Integration/acceptance verification across ZCore + Chat + Memory.
- [ ] Final Step 4 acceptance verification.

Known implementation note:
- Current JSON repository uses a temporary file and rename for writes, but the rename result is not explicitly checked.
- Malformed/unreadable JSON currently falls back to an empty in-memory list. This is acceptable for the current foundation tests but should be reviewed before final memory acceptance because silent data loss/recovery behavior is not yet a complete policy.

Step 4 is NOT COMPLETE. The current implementation is the persistent-memory foundation, not the finished conversation-memory/retrieval/security subsystem.

---

## 5. Voice System
Status: [ ] NOT STARTED

Planned:
- Speech input/output.
- Hindi/Hinglish-friendly interaction.
- STT/TTS provider abstraction.
- Future wake-word / “Wake up Z” capability.
- Voice integrates with Z Core rather than becoming a separate assistant brain.

---

## 6. Vision System
Status: [ ] NOT STARTED

Planned:
- Image understanding.
- OCR/document understanding.
- Multimodal routing.
- Specialist vision models/providers as appropriate.
- Replaceable model/provider architecture.

---

## 7. Tool System
Status: [ ] NOT STARTED

Planned:
- Capability/skill registry.
- Coding, files, software and creation capabilities where authorized.
- Safe tool execution boundaries.

---

## 8. Agent / Task System
Status: [ ] NOT STARTED

Planned:
- Multi-step planning.
- Task state.
- Execution/verification.
- Recovery and retry.

---

## 9. Model & Resource Management
Status: [ ] NOT STARTED

Planned:
- Hardware/resource detection.
- CPU/GPU/Vulkan awareness.
- CPU fallback.
- Model storage and lifecycle management.
- Resource-aware provider/model routing.

---

## 10. Avatar & Interaction
Status: [ ] NOT STARTED

Planned:
- Avatar is Z/MAX's connected visual/interactive representation, not a separate AI brain.
- Multimodal companion layer.
- Voice, vision, memory, interaction and system-state integration.
- Avatar states reflect actual Z runtime state rather than decorative animation.

---

## 11. Complete Integration + Testing
Status: [ ] NOT STARTED

Planned:
- Cross-layer integration of Steps 1–10.
- End-to-end provider/model/chat/memory/voice/vision/tool/agent/resource/avatar checks.
- Failure/recovery verification.
- Security/authorization verification.
- Final regression testing.

---

## 12. Final Z Release / Comprehensive Device Verification
Status: [ ] NOT STARTED

Definition:
- Step 12 is the final comprehensive ADB/device verification gate after Steps 1–11 implementation work is complete.
- Device-dependent behavior must be tested here where hardware/ADB is available.
- Any failures found at this gate must be fixed and retested before Step 12 can be marked COMPLETE.
- Final release packaging must not be treated as complete merely because an APK assembles.

---

## 13. Self-Development
Status: [LOCKED]

Rule:
Self-development must NOT be implemented or enabled until Steps 1–12 are fully implemented, integrated, tested and verified.

Required controlled loop:
Need detected
→ Plan
→ Policy / owner authorization check
→ Change
→ Build
→ Test
→ Verify
→ Accept OR Rollback
→ Record

---
# ACTIVE ARCHITECTURE

ZApplication
    |
    v
ZCore
    |
    v
ZCoreImpl
    |
    v
MainActivity
    |
    v
MainNavigation
    |
    v
MainScreen

Future layers will connect through the appropriate architecture layer.
They must not create duplicate independent Z cores.

---

# LEGACY / TEMPLATE CODE

These files came from the generated Android template and are NOT currently part
of the active Foundation flow:

- [~] app/src/main/java/com/example/z/data/DataRepository.kt
- [~] app/src/main/java/com/example/z/ui/main/MainScreenViewModel.kt
- [~] app/src/main/java/com/example/z/NavigationKeys.kt

IMPORTANT:
Legacy does NOT automatically mean delete.
Before removal, references/dependencies must be checked.
No active Z feature may be implemented twice.

---

# CURRENT ACTIVE FOUNDATION / AI / CHAT / MEMORY FILES

Core / application:
- app/src/main/java/com/example/z/core/ZCore.kt
- app/src/main/java/com/example/z/core/ZCoreImpl.kt
- app/src/main/java/com/example/z/ZApplication.kt
- app/src/main/java/com/example/z/MainActivity.kt
- app/src/main/java/com/example/z/Navigation.kt
- app/src/main/java/com/example/z/ui/main/MainScreen.kt

AI / model:
- app/src/main/java/com/example/z/ai/
- app/src/main/java/com/example/z/ai/... provider/runtime/JNI/model components as present in the working tree
- app/src/main/cpp/ native llama.cpp/JNI sources as present in the working tree

Chat:
- app/src/main/java/com/example/z/chat/ChatMessage.kt
- app/src/main/java/com/example/z/chat/ChatViewModel.kt
- app/src/main/java/com/example/z/chat/ChatScreen.kt
- app/src/main/java/com/example/z/chat/ streaming interfaces/components as present in the working tree

Memory:
- app/src/main/java/com/example/z/memory/MemoryEntry.kt
- app/src/main/java/com/example/z/memory/MemoryRepository.kt
- app/src/main/java/com/example/z/memory/JsonMemoryRepository.kt
- app/src/main/java/com/example/z/memory/InMemoryMemoryRepository.kt

Tests:
- app/src/test/java/com/example/z/core/ZCoreImplTest.kt
- app/src/test/java/com/example/z/ZApplicationCoreTest.kt
- app/src/test/java/com/example/z/ai/
- app/src/test/java/com/example/z/chat/
- app/src/test/java/com/example/z/memory/JsonMemoryRepositoryTest.kt

Important:
- The repository currently contains additional uncommitted/untracked project files and backup files in the working tree.
- Do NOT use `git add .` blindly.
- Stage only the exact files belonging to a reviewed milestone.
- Preserve backups unless their removal is explicitly reviewed and approved.

---
# VERIFIED COMMANDS / RESULTS

Foundation history:
- `./gradlew compileDebugKotlin` — BUILD SUCCESSFUL.
- `./gradlew testDebugUnitTest` — BUILD SUCCESSFUL.
- `./gradlew assembleDebug` — BUILD SUCCESSFUL.

Step 2 / provider/runtime verification history:
- `./gradlew :app:compileDebugKotlin --no-daemon --max-workers=1` — BUILD SUCCESSFUL.
- `./gradlew :app:testDebugUnitTest --no-daemon --max-workers=1` — BUILD SUCCESSFUL.
- `./gradlew :app:compileDebugAndroidTestKotlin --no-daemon --max-workers=1` — BUILD SUCCESSFUL.
- Native llama.cpp/JNI build verification passed.
- Debug APK assembly verification passed.

Step 4 memory verification:
- `./gradlew :app:compileDebugKotlin` — BUILD SUCCESSFUL after memory integration.
- `./gradlew app:testDebugUnitTest` — BUILD SUCCESSFUL.
- Final Step 4 unit-test run: 29 tests completed successfully.
- Step 4 commit: `4cb5f46`.
- `git pull --ff-only origin main` — Already up to date.
- `git rev-parse --short HEAD` — `4cb5f46`.

Important:
- A successful build/test run does not by itself mark a roadmap step COMPLETE.
- Runtime/device-dependent acceptance remains separate.

---
# KNOWN WARNINGS / ISSUES

- No physical Android device/emulator/ADB runtime has been available in the current workflow.
- Therefore real Android GGUF inference/streaming, Android cancellation behavior and Android Keystore runtime behavior remain unverified.
- No real API credential has been added; real online API request/response remains unverified.
- Resource-aware provider selection is not yet a complete implementation.
- Step 3 still needs persistent conversation integration with Memory.
- Step 4 JSON memory foundation has the write/rename and malformed-JSON recovery behaviors documented above and needs a later robustness/policy review.
- Gradle/Java restricted-native-access warnings appeared during builds/tests; they did not cause build failure.
- The existing `ZApplicationCoreTest` does not replace real Android Application/device lifecycle verification.
- The working tree contains unrelated/untracked files and backups. This is a repository hygiene risk if files are staged indiscriminately.
- Historical milestone notes below are retained intentionally; they describe earlier states and must not be mistaken for the current roadmap status.

---
# DEVELOPMENT RULES

1. Never mark anything COMPLETE without implementation + integration + testing + verification.
2. Never create duplicate versions of an existing Z capability.
3. Legacy/template code must be identified before deletion.
4. Do not delete code merely because it looks duplicated.
5. Check references before removing legacy code.
6. Keep one authoritative implementation for each capability.
7. Build layer-by-layer.
8. The AI model is one layer of Z, not the whole system.
9. Memory must remain part of the planned architecture.
10. Self-development remains locked until Steps 1–12 are complete.
11. Record important commands and their effects.
12. Preserve verified completed work.
13. Do not guess paths or dependencies.
14. Do not make unnecessary environment changes.
15. Every milestone must update this file.
16. The roadmap status at the top of this file is the current authoritative status; historical milestone sections are history, not a second status source.
17. Never mark a roadmap step COMPLETE solely because compilation or unit tests pass when runtime/device/service verification is part of its acceptance criteria.
18. Step 12 is the final comprehensive ADB/device verification gate for Steps 1–12; failures must be fixed and retested before Step 12 completion.
19. Step 13 Self-Development remains LOCKED until Step 12 is fully verified.

# CURRENT VERIFIED SNAPSHOT

Date: 2026-09-18

Current roadmap status:
- Step 1 Foundation & Architecture: IN PROGRESS.
- Step 2 AI Brain / Model Layer: IN PROGRESS.
- Step 3 Chat System: IN PROGRESS.
- Step 4 Memory System: IN PROGRESS.
- Steps 5–12: NOT STARTED.
- Step 13 Self-Development: LOCKED.

Latest verified milestone:
- Step 4 persistent memory foundation committed as `4cb5f46` and pushed to `main`.
- Full JVM unit-test suite passed with 29 tests after Memory integration.

Latest earlier relevant commit:
- Step 3 Chat/streaming work was committed as `58904dd` before Step 4.

Critical rule:
- No step is marked COMPLETE until its implementation, integration, tests and required verification all succeed.

---

## VERIFIED MILESTONE — FOUNDATION WIRING / LEGACY CLEANUP
Date: 2026-09-16

Verified:
- ZCore / ZCoreImpl foundation exists.
- ZApplication initializes and starts ZCore.
- MainActivity obtains the shared ZCore.
- MainActivity passes ZCore to MainNavigation.
- MainNavigation passes ZCore to MainScreen.
- MainScreen displays the ZCore state.
- Legacy DataRepository, MainScreenViewModel, NavigationKeys and their obsolete test were removed after reference checking.
- Final reference check for DataRepository/MainScreenViewModel/NavigationKeys/MainKey returned no results.

Build verification:
- ./gradlew compileDebugKotlin — BUILD SUCCESSFUL
- ./gradlew testDebugUnitTest — BUILD SUCCESSFUL
- ./gradlew assembleDebug — BUILD SUCCESSFUL

Important:
- Runtime launch on a physical device/emulator has not yet been verified.
- Therefore Step 1 is NOT marked fully COMPLETE until runtime verification is performed.

## VERIFIED MILESTONE — AI BRAIN FOUNDATION INTEGRATION

Date: 2026-09-16

Current step:
Step 2 — AI Brain / Model Layer

Verified work:
- AIProvider interface
- AIRequest and AIResponse
- AITaskType
- AIProviderCapabilities
- AIProviderRegistry
- AIProviderRouter
- LocalAIProvider
- AIBrain interface
- AIBrainImpl
- ZCore integration with AIBrain
- ZCore AI integration unit test

Verification:
- ./gradlew compileDebugKotlin — BUILD SUCCESSFUL
- ./gradlew testDebugUnitTest — BUILD SUCCESSFUL

Current limitation at that historical milestone:
- LocalAIProvider was intentionally unavailable because no real model runtime was connected yet.
- No fake AI response was used.
- This limitation was later addressed by the native runtime integration milestone.
- Android physical-device/emulator runtime verification remains pending.

Status:
Step 1 — INCOMPLETE (runtime verification pending)
Step 2 — IN PROGRESS

Next exact target:
Create the model-runtime abstraction required for real local/model-provider integration, then implement and verify the first real runtime adapter.

Permanent rule:
Do not mark a step COMPLETE until implementation, integration, testing, and verification are all successful.

## VERIFIED MILESTONE — MODEL RUNTIME FOUNDATION
Date: 2026-09-16

Step 2 — AI Brain / Model Layer remains IN PROGRESS.

Verified implementation:
- ModelRuntime abstraction added.
- UnavailableModelRuntime added for explicit unavailable-runtime behavior.
- LocalAIProvider is wired to ModelRuntime.
- ZCore registers LocalAIProvider with UnavailableModelRuntime.
- ModelRuntimeInfo added to represent runtime/model/load state.

Verification:
- ./gradlew testDebugUnitTest — BUILD SUCCESSFUL
- ModelRuntimeInfo tests passed.
- Existing AI Brain/provider/runtime tests remained passing.

Current limitation at that historical milestone:
- The first runtime foundation was not yet connected at that point.
- This was later superseded by the native llama.cpp integration milestone.
- Android runtime/device verification remains pending.

Next exact target:
- Integrate the first real model runtime adapter without replacing the existing ModelRuntime/AIProvider architecture.
- Verify model loading, availability, generation, failure handling, and AI Brain integration.

Step 2 must not be marked COMPLETE until real runtime integration and verification are successful.

## VERIFIED MILESTONE — ONLINE/API PROVIDER FOUNDATION

Date: 2026-09-17

Step 2 AI Brain / Model Layer remains IN PROGRESS.

Verified implementation:
- OpenAI-compatible generic Online AI Provider added.
- Provider uses the existing AIProvider contract.
- Online provider supports GENERAL, REASONING, CODING and FAST_RESPONSE task types.
- Online provider registered in ZCoreImpl without replacing the existing LocalAIProvider.
- AIProviderConfig added for endpoint/model/API-key configuration.
- SecureApiKeyStore added using Android Keystore-backed AES-GCM encryption.
- Online provider availability tests added and passed.
- Kotlin compilation passed after Online Provider and secure-storage integration.
- Android instrumented-test source compilation passed for SecureApiKeyStoreTest.

Verification:
- ./gradlew :app:compileDebugKotlin --no-daemon --max-workers=1 — BUILD SUCCESSFUL
- ./gradlew :app:testDebugUnitTest --no-daemon --max-workers=1 — BUILD SUCCESSFUL
- ./gradlew :app:compileDebugAndroidTestKotlin --no-daemon --max-workers=1 — BUILD SUCCESSFUL

Not verified yet:
- Real online API request/response.
- Runtime Android Keystore save/retrieve/clear test; no device/emulator is currently available.
- Real API credentials have NOT been added.
- Real GGUF/native model inference remained device-unverified at that milestone.

Status:
Online/API Provider Foundation = INCOMPLETE.
Step 2 = IN PROGRESS.

Next exact target:
Verify the Online Provider through a real authorized API configuration without exposing credentials in source code, then continue real GGUF/native runtime integration.



---

# CONSOLIDATED Z PROJECT REQUIREMENTS / CARRY-FORWARD
Date consolidated: 2026-09-18

## A. Project identity and development constraints

- Z is the product identity and personal AI assistant/system.
- Repository: Z-AI; branch: main; Android package: com.example.z.
- Development is phone-first through GitHub Codespaces; no PC/laptop dependency is required for the current workflow.
- Do not use Termux for this project unless the owner explicitly changes this rule.
- Preserve the existing project; extend verified work instead of rebuilding from scratch.
- Current Android stack: Kotlin + Jetpack Compose + Material 3; min SDK 24; target/compile SDK currently 36; primary native ABI currently arm64-v8a.
- Native stack includes llama.cpp and the Z JNI bridge.
- Z architecture/data contracts should remain device-independent so the project can later move to stronger hardware without changing Z identity.

## B. Authoritative architecture direction

Z is a modular AI system, not a single LLM.

Long-term architecture:
Z Core
→ Persistent Memory
→ Model / Provider Layer
→ Intelligence / Capability Router
→ Agent / Planner
→ Tools / Skills / Capability Registry
→ Voice
→ Vision / Multimodal
→ Security / Permissions / Privacy
→ Verification / Evaluation / Trace
→ Controlled Self-Development (locked until Steps 1–12)

Rules:
- Model != Z.
- Models/providers are replaceable intelligence modules.
- Model Router selects an appropriate model/provider for a task.
- Capability Router decides whether an existing deterministic capability, tool, model, specialist service, or authorized external service is appropriate.
- Do not invoke a large LLM when a deterministic capability can safely solve the task.
- Intended strategy: LOCAL-FIRST + HYBRID + MULTI-MODEL.
- Online services are optional/authorized capabilities, not a permanent architectural dependency.
- Multiple authorized API providers/keys may be supported securely; multiple keys are not separate brains and must never be used to bypass provider limits or policies.

## C. Model/provider capability direction

Potential provider categories:
- Local/on-device GGUF models.
- General/frontier reasoning providers.
- Coding-specialized providers/models.
- Vision/multimodal providers.
- Fast/low-latency models.
- Specialist models/services such as embeddings, OCR, speech and reranking.
- Authorized fallback providers.

Examples discussed (not hard-coded requirements):
OpenAI GPT, Anthropic Claude, Google Gemini, Qwen, Llama, Mistral and DeepSeek.

Future provider selection should consider:
- Task/capability
- Privacy
- Device hardware
- CPU/GPU/Vulkan availability
- RAM/storage
- Latency
- Cost/authorization
- Model availability
- Failure/retry state

## D. Controlled self-development

Self-development is Step 13 and is LOCKED.

It must not be implemented or enabled until Steps 1–12 are fully implemented, integrated, tested and verified.

Required loop:
Need detected
→ Plan
→ Policy / owner authorization check
→ Change
→ Build
→ Test
→ Verify
→ Accept OR Rollback
→ Record

Self-development must be controlled, auditable and fail-closed. It must not bypass owner permissions, security boundaries or verification.

## E. Step completion rule

A roadmap step is COMPLETE only when:
1. Implementation exists.
2. Integration is complete.
3. Tests pass.
4. Verification is successful.

Compilation alone is never sufficient.

Runtime/device-dependent items remain pending when the required device/service/credential is unavailable.

## F. Step 2 current carry-forward

Verified implementation/build/test coverage:
- AI contracts and task types.
- Provider registry and routing.
- AIBrain integration.
- ModelRuntime abstraction.
- Unavailable runtime behavior.
- Native llama.cpp runtime adapter.
- JNI bridge/native build.
- GGUF ModelManager/import flow.
- Online OpenAI-compatible provider.
- Secure API-key storage layer.
- Kotlin compilation.
- Native compilation.
- Unit tests.
- Debug APK assembly.
- JNI benchModel naming verification/correction.

Step 2 remains IN PROGRESS because:
- Real Android GGUF inference has not been runtime-tested on a physical device/emulator.
- Real online API request/response has not been tested with authorized credentials.
- Android Keystore runtime save/retrieve/clear has not been device-tested.
- Resource-aware provider selection is not yet a complete implementation.

Do not recreate already-implemented components merely because runtime verification is pending.

## G. Step 3 Chat System requirements

When Step 3 starts, build on the existing Z Core and AI Brain. Do not create a second independent AI/chat core.

Requirements:
- Conversation state.
- User/assistant message model.
- Request/response lifecycle.
- Streaming generation lifecycle.
- Send/cancel/stop generation behavior.
- Loading/thinking/error states.
- Retry handling.
- Persistent conversation integration with Memory later.
- Keyboard-safe input layout.
- Automatic scroll to latest message.
- Long-press message actions such as edit/delete where appropriate.
- Responsive UI during inference.
- Local and online providers use the same AI Brain/provider architecture.

## H. Memory direction

Step 4:
- Persistent structured memory.
- Conversation memory.
- Retrieval.
- Privacy/security boundaries.
- Memory independent of any single model/provider.
- Structured state for future self-development/trace rather than relying on chat history alone.

## I. Voice direction

Step 5:
- Speech input/output.
- Hindi/Hinglish-friendly interaction.
- STT/TTS provider abstraction.
- Future wake-word / “Wake up Z” capability.
- Voice integrates with Z Core rather than becoming a separate assistant brain.

## J. Vision direction

Step 6:
- Image understanding.
- OCR/document understanding.
- Multimodal routing.
- Specialist vision models/providers as appropriate.
- Replaceable model/provider architecture.

## K. Tools / Agent / Resource / Avatar direction

Step 7 Tools:
- Capability/skill registry.
- Coding, files, software and creation capabilities where authorized.
- Safe tool execution boundaries.

Step 8 Agent/Task:
- Multi-step planning.
- Task state.
- Execution/verification.
- Recovery and retry.

Step 9 Model/Resource:
- Hardware/resource detection.
- CPU/GPU/Vulkan awareness.
- CPU fallback.
- Model storage and lifecycle management.
- Resource-aware routing.

Step 10 Avatar:
- Avatar is Z/MAX's connected visual/interactive representation, not a separate AI brain.
- Multimodal companion layer.
- Voice, vision, memory, interaction and system-state integration.
- Avatar states reflect actual Z runtime state rather than decorative animation.

## L. Security / authorization principles

- Owner-controlled permissions.
- Fail-closed behavior for unauthorized sensitive actions.
- API keys never hardcoded into source.
- Secrets use secure storage.
- No bypassing provider quotas, authentication, device restrictions or service policies.
- Audit important actions.
- Explicit privacy boundaries.
- Do not silently grant new external permissions or install/download external dependencies when approval is required.

## M. Capability acquisition

If Z lacks a capability:
Need detected
→ identify required capability
→ choose existing capability, local tool/model, plugin/service or authorized external provider
→ obtain/setup only through an authorized source
→ integrate
→ build/test/verify
→ record

Do not randomly download models/software or invent external services.

## N. Development workflow

- One terminal command at a time.
- Inspect real paths before modifying them.
- Prefer consolidated file changes over fragmented manual edits.
- Verify after meaningful changes.
- Do not repeat already verified work.
- Do not delete code merely because it looks duplicated.
- Check references/dependencies before removing legacy code.
- Record important commands and their effects.
- Never claim unverified completion.
- Flag duplicates, workarounds, incomplete wiring and conflicts.
- Keep moving forward; avoid repeated inspection when evidence is already sufficient.

## O. Current environment / verification limitations

- No Android physical device/emulator/ADB runtime has been available in the current workflow.
- Native GGUF inference and Android Keystore runtime behavior therefore remain device-unverified.
- No real API credential has been added.
- API infrastructure builds/tests without a real key.
- Non-failing Gradle/Java warnings remain warnings, not build failures.

## P. Persistent project preferences

- Z should eventually be a broad personal AI system, not merely a chatbot.
- Local + online multi-model capability is preferred over dependence on one fixed model.
- Controlled self-development/self-healing is desired only after the initial system is complete and verified.
- Project state must survive chat changes and prevent lost/misremembered progress.
- Final APK/release packaging happens only after required core verification.
- Actual completion matters more than optimistic percentages.
