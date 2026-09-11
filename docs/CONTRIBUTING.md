# Contributing

## Code organization

All source code lives in `src/main/java/nfcore/nftest/utils/`. The package structure:

| File                        | Responsibility                                                                                                                                                 |
| --------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `Methods.java`              | Public API. All methods here are registered as Groovy extension methods via `META-INF/nf-test-plugin`. This file should only contain thin delegation wrappers. |
| `OutputSanitizer.java`      | Processes `process.out` / `workflow.out` channels. Validates keys, applies unstable/ignore patterns, delegates to specialized utils for MD5.                   |
| `NextflowOutputFilter.java` | Filters Nextflow stdout/stderr. Removes timestamps, hashes, paths, run names. Supports sorting and additional regex patterns.                                  |
| `FileTraversalUtils.java`   | Directory walking, file collection, path resolution. Handles local and S3 paths.                                                                               |
| `ArchiveDownloader.java`    | Downloads and extracts tar/zip files from URLs.                                                                                                                |
| `YamlUtils.java`            | YAML reading and key manipulation.                                                                                                                             |
| `HashUtils.java`            | MD5 hashing utilities.                                                                                                                                         |
| `CsvUtils.java`             | CSV/TSV normalization and MD5 computation.                                                                                                                     |
| `BamUtils.java`             | BAM/SAM/CRAM read MD5 via reflection into the `nft-bam` plugin.                                                                                                |
| `VcfUtils.java`             | VCF/BCF variant MD5 via reflection into the `nft-vcf` plugin.                                                                                                  |
| `NfCoreUtils.java`          | nf-core module lifecycle: init, install, link/unlink, delete.                                                                                                  |
| `Utils.java`                | Low-level helpers: process execution, shell escaping, path utilities.                                                                                          |

### Adding new features

- New Groovy-callable functions go in `Methods.java` as thin wrappers.
- Logic goes in a dedicated utility class, not in `Methods.java`.
- New options go in the relevant options map. Extract them in a typed helper (see `OutputSanitizer.SanitizeOptions`).

### Adding new sanitization keys

- Add the key list parameter to `sanitizeOutput` in `Methods.java`.
- Add the key extraction to `OutputSanitizer.SanitizeOptions`.
- Add the processing branch in `OutputSanitizer.sanitizeOutput`.

### Code style

- Checkstyle, PMD, and SpotBugs run on every build. Fix violations before pushing.
- Throw exceptions instead of calling `System.exit()`.
- Do not swallow exceptions silently. Log and rethrow, or let the caller handle it.
- No raw types. Use `List<?>` or the concrete type.
- Utility classes have private constructors.

## Changelog

Every user-facing change must have an entry in `CHANGELOG.md`.

### Format

```
- [#N](https://github.com/nf-core/nft-utils/pull/N) Short description (@author)
```

### Rules

- One line per entry.
- No trailing periods.
- Written in imperative mood ("Add feature", not "Added feature").
- No AI slop: no "leverage", "streamline", "robust", "utilize", or other business jargon.
- Keep entries short. Link to the PR for details.
- Place entries under the correct section: `### Added`, `### Changed`, or `### Fixed`.
- Attribute every entry to its author with `(@github-handle)`.
- List new contributors under `### New Contributors` in the release where they first appear.
- Omit internal-only PRs (version bumps, release prep, pre-commit hooks).
